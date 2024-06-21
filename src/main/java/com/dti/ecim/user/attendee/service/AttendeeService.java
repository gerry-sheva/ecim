package com.dti.ecim.user.attendee.service;

import com.dti.ecim.user.attendee.dto.CreateAttendeeDto;
import com.dti.ecim.user.attendee.entity.Attendee;

import java.security.NoSuchAlgorithmException;

public interface AttendeeService {
    Attendee createAttendee(CreateAttendeeDto createAttendeeDto) throws NoSuchAlgorithmException;
}
