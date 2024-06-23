package com.dti.ecim.user.attendee.controller;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.attendee.dto.CreateAttendeeDto;
import com.dti.ecim.user.attendee.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/user/attendee")
public class AttendeeController {
    private final AttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<ResponseDto> createAttendee(@RequestBody CreateAttendeeDto createAttendeeDto) throws NoSuchAlgorithmException, BadRequestException {
        ResponseDto res = attendeeService.createAttendee(createAttendeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
