package com.dti.ecim.user.service.impl;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.auth.service.UserRoleService;
import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.user.dto.CreateOrganizerDto;
import com.dti.ecim.user.dto.OrganizerResponseDto;
import com.dti.ecim.user.entity.Attendee;
import com.dti.ecim.user.entity.Organizer;
import com.dti.ecim.user.repository.OrganizerRepository;
import com.dti.ecim.user.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerRepository organizerRepository;
    private final UserAuthRepository userAuthRepository;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ResponseDto createOrganizer(CreateOrganizerDto createOrganizerDto) {
        log.info("Creating organizer " + createOrganizerDto.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(authentication.getName());
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        Organizer organizer = new Organizer();
        organizer.setUser(userAuthOptional.get().getUser());
        organizer.setName(createOrganizerDto.getName());
        organizer.setAvatar(createOrganizerDto.getAvatar());
        organizerRepository.save(organizer);
        userRoleService.addUserRole(new AddUserRoleDto(userAuthOptional.get().getUserId(), 2L));
        return new ResponseDto("Organizer created successfully");
    }

    @Override
    public OrganizerResponseDto findOrganizerByEmail(String email) {
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmail(email);
        if (userAuthOptional.isEmpty()) {
            throw new DataNotFoundException("User not found");
        }
        Optional<Organizer> organizerOptional = organizerRepository.findById(userAuthOptional.get().getUserId());
        if (organizerOptional.isEmpty()) {
            throw new DataNotFoundException("Attendee with id " + userAuthOptional.get().getUserId() + " not found");
        }
        OrganizerResponseDto organizerResponseDto = new OrganizerResponseDto();
        organizerResponseDto.setUserId(organizerOptional.get().getUserId());
        return organizerResponseDto;
    }
}
