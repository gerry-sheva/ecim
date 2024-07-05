package com.dti.ecim.user.controller;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.dto.attendee.CreateAttendeeRequestDto;
import com.dti.ecim.user.dto.attendee.CreateAttendeeResponseDto;
import com.dti.ecim.user.service.UserService;
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
@RequestMapping("/api/v1/attendee")
public class AttendeeController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateAttendeeResponseDto> createAttendee(@RequestBody CreateAttendeeRequestDto createAttendeeRequestDto) throws NoSuchAlgorithmException, BadRequestException {
        CreateAttendeeResponseDto res = userService.createAttendee(createAttendeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public String plon() {
        return "plin plin plon";
    }

    @GetMapping("/secure")
    public String plin() {
        return "Plin Plin Plon";
    }
}
