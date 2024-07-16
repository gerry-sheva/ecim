package com.dti.ecim.user.service.impl;

import com.dti.ecim.auth.enums.Role;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.discount.dto.ClaimDiscountRequestDto;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.user.dto.attendee.CreateAttendeeRequestDto;
import com.dti.ecim.user.dto.attendee.CreateAttendeeResponseDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerRequestDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerResponseDto;
import com.dti.ecim.user.entity.Attendee;
import com.dti.ecim.user.entity.Organizer;
import com.dti.ecim.user.entity.Referral;
import com.dti.ecim.user.repository.AttendeeRepository;
import com.dti.ecim.user.repository.OrganizerRepository;
import com.dti.ecim.user.repository.ReferralRepository;
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
    private final AttendeeRepository attendeeRepository;
    private final OrganizerRepository organizerRepository;
    private final ReferralRepository referralRepository;

    private final AuthService authService;
    private final DiscountService discountService;

    @Override
    @Transactional
    public CreateAttendeeResponseDto createAttendee(CreateAttendeeRequestDto requestDto) throws NoSuchAlgorithmException, BadRequestException {
        var userAuth = authService.getCurrentUser();
        Attendee newAttendee = new Attendee();
        newAttendee.setUser(userAuth);
        newAttendee.setFname(requestDto.getFname());
        newAttendee.setLname(requestDto.getLname());
        newAttendee.setDob(parseDate(requestDto.getDob()));
        newAttendee.setRefCode(generateReferralCode(userAuth.getEmail()));
        newAttendee.setPoints(0);
        newAttendee.setContact(requestDto.getContact());
        attendeeRepository.save(newAttendee);

        if (requestDto.getReferralCode() != null) {
            log.info("Attempting to add referral using code: " + requestDto.getReferralCode());
            Optional<Attendee> referral = attendeeRepository.findByRefCode(requestDto.getReferralCode());
            if (referral.isEmpty()) {
                throw new BadRequestException("Referral code is invalid");
            }
            boolean isAlreadyExists = referralIsAlreadyExist(referral.get().getAttendeeId(), userAuth.getId());
            if (!isAlreadyExists) {
                log.info("Adding referral using code: " + requestDto.getReferralCode());
                Referral newReferral = new Referral();
                newReferral.setReferralId(referral.get().getAttendeeId());
                newReferral.setReferral(referral.get());
                newReferral.setReferreeId(userAuth.getId());
                newReferral.setReferree(newAttendee);
                referralRepository.save(newReferral);
                discountService.addPoint(referral.get().getAttendeeId());
                discountService.claimDiscount(new ClaimDiscountRequestDto("d2873ef5-acf1-4be8-9111-3b247d3516bf"));
            }
        }

        String token = authService.addRole(Role.ATTENDEE);

        CreateAttendeeResponseDto responseDto = new CreateAttendeeResponseDto();
        responseDto.setFname(newAttendee.getFname());
        responseDto.setLname(newAttendee.getLname());
        responseDto.setDob(newAttendee.getDob());
        responseDto.setContact(newAttendee.getContact());
        responseDto.setReferralCode(newAttendee.getRefCode());
        responseDto.setRole(Role.ATTENDEE.name());
        responseDto.setToken(token);

        return responseDto;
    }

    @Override
    @Transactional
    public CreateOrganizerResponseDto createOrganizer(CreateOrganizerRequestDto requestDto) {
        var userAuth = authService.getCurrentUser();
        Organizer newOrganizer = new Organizer();
        newOrganizer.setUser(userAuth);
        newOrganizer.setName(requestDto.getName());
        newOrganizer.setAvatar(requestDto.getAvatar());
        organizerRepository.save(newOrganizer);

        String token = authService.addRole(Role.ORGANIZER);

        CreateOrganizerResponseDto responseDto = new CreateOrganizerResponseDto();
        responseDto.setName(newOrganizer.getName());
        responseDto.setRole(Role.ORGANIZER.name());
        responseDto.setToken(token);

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
