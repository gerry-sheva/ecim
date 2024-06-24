package com.dti.ecim.user.organizer.service.impl;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.auth.repository.UserAuthRepository;
import com.dti.ecim.auth.service.UserRoleService;
import com.dti.ecim.dto.ResponseDto;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.user.organizer.dto.CreateOrganizerDto;
import com.dti.ecim.user.organizer.entity.Organizer;
import com.dti.ecim.user.organizer.repository.OrganizerRepository;
import com.dti.ecim.user.organizer.service.OrganizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
}
