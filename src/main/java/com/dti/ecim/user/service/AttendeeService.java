package com.dti.ecim.user.service;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.dto.CreateAttendeeDto;
import org.apache.coyote.BadRequestException;

import java.security.NoSuchAlgorithmException;

public interface AttendeeService {
    ResponseDto createAttendee(CreateAttendeeDto createAttendeeDto) throws NoSuchAlgorithmException, BadRequestException;
}
