package com.dti.ecim.user.service.impl;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.auth.service.UserRoleService;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.exceptions.DataNotFoundException;
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
    private final AttendeeRepository attendeeRepository;
    private final OrganizerRepository organizerRepository;
    private final ReferralRepository referralRepository;

    private final UserRoleService userRoleService;
    private final AuthService authService;
    private final DiscountService discountService;

    @Override
    public User registerUser() {
        return userRepository.save(new User());
    }

    private User retrieveUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new DataNotFoundException("User with id " + userId + " not found");
        }
        return user.get();
    }

    @Override
    @Transactional
    public CreateAttendeeResponseDto createAttendee(CreateAttendeeRequestDto requestDto) throws NoSuchAlgorithmException, BadRequestException {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        User user = retrieveUser(userIdResponseDto.getId());
        log.info(userIdResponseDto.getId().toString());
        Attendee newAttendee = new Attendee();
        newAttendee.setUser(user);
        newAttendee.setFname(requestDto.getFname());
        newAttendee.setLname(requestDto.getLname());
        newAttendee.setDob(parseDate(requestDto.getDob()));
        newAttendee.setRefCode(generateReferralCode(userIdResponseDto.getEmail()));
        newAttendee.setPoints(0);
        newAttendee.setContact(requestDto.getContact());
        attendeeRepository.save(newAttendee);

        if (requestDto.getReferralCode() != null) {
            log.info("Attempting to add referral using code: " + requestDto.getReferralCode());
            Optional<Attendee> referral = attendeeRepository.findByRefCode(requestDto.getReferralCode());
            if (referral.isEmpty()) {
                throw new BadRequestException("Referral code is invalid");
            }
            boolean isAlreadyExists = referralIsAlreadyExist(referral.get().getUserId(), userIdResponseDto.getId());
            if (!isAlreadyExists) {
                log.info("Adding referral using code: " + requestDto.getReferralCode());
                Referral newReferral = new Referral();
                newReferral.setReferralId(referral.get().getUserId());
                newReferral.setReferral(referral.get());
                newReferral.setReferreeId(userIdResponseDto.getId());
                newReferral.setReferree(newAttendee);
                referralRepository.save(newReferral);
                discountService.addPoint(referral.get().getUserId());
            }
        }
        userRoleService.addUserRole(new AddUserRoleDto(userIdResponseDto.getId(), 1L));

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
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        User user = retrieveUser(userIdResponseDto.getId());
        Organizer newOrganizer = new Organizer();
        newOrganizer.setUser(user);
        newOrganizer.setName(requestDto.getName());
        newOrganizer.setAvatar(requestDto.getAvatar());
        organizerRepository.save(newOrganizer);

        userRoleService.addUserRole(new AddUserRoleDto(userIdResponseDto.getId(), 2L));

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
