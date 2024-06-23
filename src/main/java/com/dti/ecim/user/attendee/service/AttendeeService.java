package com.dti.ecim.user.attendee.service;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.attendee.dto.CreateAttendeeDto;
import org.apache.coyote.BadRequestException;

import java.security.NoSuchAlgorithmException;

public interface AttendeeService {
    ResponseDto createAttendee(CreateAttendeeDto createAttendeeDto) throws NoSuchAlgorithmException, BadRequestException;
}