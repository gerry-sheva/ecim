package com.dti.ecim.user.service;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.dto.CreateAttendeeDto;
import com.dti.ecim.user.entity.Attendee;
import org.apache.coyote.BadRequestException;

import java.security.NoSuchAlgorithmException;

public interface AttendeeService {
    ResponseDto createAttendee(CreateAttendeeDto createAttendeeDto) throws NoSuchAlgorithmException, BadRequestException;
    Attendee findAttendeeById(Long id);
    Attendee findAttendeeByEmail(String email);
}
