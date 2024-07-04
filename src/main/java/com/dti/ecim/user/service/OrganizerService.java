package com.dti.ecim.user.service;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.dto.CreateOrganizerDto;
import com.dti.ecim.user.dto.OrganizerResponseDto;
import com.dti.ecim.user.entity.Organizer;

public interface OrganizerService {
    ResponseDto createOrganizer(CreateOrganizerDto createOrganizerDto);
    OrganizerResponseDto findOrganizerByEmail(String email);
}
