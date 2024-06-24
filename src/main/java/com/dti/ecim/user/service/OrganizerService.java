package com.dti.ecim.user.service;

import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.user.dto.CreateOrganizerDto;

public interface OrganizerService {
    ResponseDto createOrganizer(CreateOrganizerDto createOrganizerDto);
}
