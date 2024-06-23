package com.dti.ecim.user.organizer.service;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.organizer.dto.CreateOrganizerDto;

public interface OrganizerService {
    ResponseDto createOrganizer(CreateOrganizerDto createOrganizerDto);
}
