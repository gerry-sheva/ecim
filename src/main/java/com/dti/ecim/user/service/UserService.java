package com.dti.ecim.user.service;

import com.dti.ecim.user.dto.attendee.CreateAttendeeRequestDto;
import com.dti.ecim.user.dto.attendee.CreateAttendeeResponseDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerRequestDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerResponseDto;
import org.apache.coyote.BadRequestException;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    CreateAttendeeResponseDto createAttendee(CreateAttendeeRequestDto requestDto) throws NoSuchAlgorithmException, BadRequestException;
    CreateOrganizerResponseDto createOrganizer(CreateOrganizerRequestDto requestDto);

    boolean referralIsAlreadyExist(Long referralId, Long referreeId) throws BadRequestException;
}
