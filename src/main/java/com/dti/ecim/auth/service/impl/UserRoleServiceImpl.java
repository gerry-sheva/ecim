package com.dti.ecim.auth.service.impl;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import com.dti.ecim.auth.entity.UserRole;
import com.dti.ecim.auth.repository.RoleRepository;
import com.dti.ecim.auth.repository.UserRoleRepository;
import com.dti.ecim.auth.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Override
    public void addUserRole(AddUserRoleDto addUserRoleDto) {
        UserRole newUserRole = new UserRole();
        newUserRole.setUserId(addUserRoleDto.getUserId());
        newUserRole.setRoleId(addUserRoleDto.getRoleId());
        userRoleRepository.save(newUserRole);
    }
}
