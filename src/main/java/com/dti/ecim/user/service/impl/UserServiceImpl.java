package com.dti.ecim.user.service.impl;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.entity.UserRole;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.auth.repository.UserRoleRepository;
import com.dti.ecim.auth.service.UserRoleService;
import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.user.dto.UserIdResponseDto;
import com.dti.ecim.user.dto.attendee.CreateAttendeeRequestDto;
import com.dti.ecim.user.dto.attendee.CreateAttendeeResponseDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerRequestDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerResponseDto;
import com.dti.ecim.user.entity.Attendee;
import com.dti.ecim.user.entity.Organizer;
import com.dti.ecim.user.entity.Referral;
import com.dti.ecim.user.entity.User;
import com.dti.ecim.user.repository.AttendeeRepository;
import com.dti.ecim.user.repository.OrganizerRepository;
import com.dti.ecim.user.repository.ReferralRepository;
import com.dti.ecim.user.repository.UserRepository;
import com.dti.ecim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.dti.ecim.user.helper.UserHelper.generateReferralCode;
import static com.dti.ecim.user.helper.UserHelper.parseDate;

@RequiredArgsConstructor
@Service
@Log
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthRepository authRepository;
    private final AttendeeRepository attendeeRepository;
    private final OrganizerRepository organizerRepository;
    private final ReferralRepository referralRepository;

    private final UserRoleService userRoleService;

    @Override
    public User registerUser() {
        return userRepository.save(new User());
    }

    @Override
    public UserIdResponseDto getCurrentUserId() {
        Optional<UserAuth> userAuthOptional = authRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        return new UserIdResponseDto(userAuthOptional.get().getUserId());
    }

    @Override
    @Transactional
    public CreateAttendeeResponseDto createAttendee(CreateAttendeeRequestDto requestDto) throws NoSuchAlgorithmException, BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserAuth> userAuthOptional = authRepository.findByEmail(authentication.getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        Attendee newAttendee = new Attendee();
        newAttendee.setUser(userAuthOptional.get().getUser());
        newAttendee.setFname(requestDto.getFname());
        newAttendee.setLname(requestDto.getLname());
        newAttendee.setDob(parseDate(requestDto.getDob()));
        newAttendee.setRefCode(generateReferralCode(authentication.getName()));
        newAttendee.setPoints(0);
        newAttendee.setContact(requestDto.getContact());
        attendeeRepository.save(newAttendee);

        if (requestDto.getReferralCode() != null) {
            log.info("Attempting to add referral using code: " + requestDto.getReferralCode());
            Optional<Attendee> referral = attendeeRepository.findByRefCode(requestDto.getReferralCode());
            if (referral.isEmpty()) {
                throw new BadRequestException("Referral code is invalid");
            }
            boolean isAlreadyExists = referralIsAlreadyExist(referral.get().getUserId(), userAuthOptional.get().getUserId());
            if (!isAlreadyExists) {
                log.info("Adding referral using code: " + requestDto.getReferralCode());
                Referral newReferral = new Referral();
                newReferral.setReferralId(referral.get().getUserId());
                newReferral.setReferral(referral.get());
                newReferral.setReferreeId(userAuthOptional.get().getUserId());
                newReferral.setReferree(newAttendee);
                referralRepository.save(newReferral);
            }
        }
        userRoleService.addUserRole(new AddUserRoleDto(userAuthOptional.get().getUserId(), 1L));

        CreateAttendeeResponseDto responseDto = new CreateAttendeeResponseDto();
        responseDto.setFname(newAttendee.getFname());
        responseDto.setLname(newAttendee.getLname());
        responseDto.setDob(newAttendee.getDob());
        responseDto.setContact(newAttendee.getContact());
        responseDto.setReferralCode(newAttendee.getRefCode());
        responseDto.setRole("ATTENDEE");

        return responseDto;
    }

    @Override
    public CreateOrganizerResponseDto createOrganizer(CreateOrganizerRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserAuth> userAuthOptional = authRepository.findByEmail(authentication.getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        Organizer newOrganizer = new Organizer();
        newOrganizer.setUser(userAuthOptional.get().getUser());
        newOrganizer.setName(requestDto.getName());
        newOrganizer.setAvatar(requestDto.getAvatar());
        organizerRepository.save(newOrganizer);

        userRoleService.addUserRole(new AddUserRoleDto(userAuthOptional.get().getUserId(), 2L));

        CreateOrganizerResponseDto responseDto = new CreateOrganizerResponseDto();
        responseDto.setName(newOrganizer.getName());
        responseDto.setRole("ORGANIZER");
        return responseDto;
    }

    @Override
    public boolean referralIsAlreadyExist(Long referralId, Long referreeId) throws BadRequestException {
        Optional<Referral> referral = referralRepository.findByReferralIdAndReferreeId(referralId, referreeId);
        if (referral.isPresent()) {
            throw new BadRequestException("Referral relationship already exists!");
        }
        return false;
    }
}
