package com.dti.ecim.user.organizer.controller;


import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.organizer.dto.CreateOrganizerDto;
import com.dti.ecim.user.organizer.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/organizer")
public class OrganizerController {
    private final OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<ResponseDto> createOrganizer(@RequestBody CreateOrganizerDto createOrganizerDto) {
        ResponseDto responseDto = organizerService.createOrganizer(createOrganizerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
