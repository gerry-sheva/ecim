package com.dti.ecim.user.controller;

import com.dti.ecim.response.Response;
import com.dti.ecim.user.dto.attendee.CreateAttendeeRequestDto;
import com.dti.ecim.user.dto.attendee.CreateAttendeeResponseDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerRequestDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerResponseDto;
import com.dti.ecim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/organizer")
    public ResponseEntity<?> createOrganizer(@RequestBody CreateOrganizerRequestDto createOrganizerRequestDto) throws BadRequestException {
        CreateOrganizerResponseDto res = userService.createOrganizer(createOrganizerRequestDto);
        return Response.success(HttpStatus.CREATED.value(), "Organizer created successfully", res);
    }

    @PostMapping("/attendee")
    public ResponseEntity<?> createAttendee(@RequestBody CreateAttendeeRequestDto createAttendeeRequestDto) throws NoSuchAlgorithmException, BadRequestException {
        CreateAttendeeResponseDto res = userService.createAttendee(createAttendeeRequestDto);
        return Response.success(HttpStatus.CREATED.value(), "Attendee created successfully", res);
    }
}
