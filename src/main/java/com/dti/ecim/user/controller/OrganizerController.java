package com.dti.ecim.user.controller;


import com.dti.ecim.user.dto.organizer.CreateOrganizerRequestDto;
import com.dti.ecim.user.dto.organizer.CreateOrganizerResponseDto;
import com.dti.ecim.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/organizer")
public class OrganizerController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateOrganizerResponseDto> createOrganizer(@RequestBody CreateOrganizerRequestDto createOrganizerRequestDto) {
        CreateOrganizerResponseDto responseDto = userService.createOrganizer(createOrganizerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/secure")
    public String plon() {
        return "plin plin plon";
    }
}
