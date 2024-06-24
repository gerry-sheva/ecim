package com.dti.ecim.auth.service.impl;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import com.dti.ecim.auth.entity.UserRole;
import com.dti.ecim.auth.repository.RoleRepository;
import com.dti.ecim.auth.repository.UserRoleRepository;
import com.dti.ecim.auth.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<GrantedAuthority> retrieveRoles(Long userId) {
        List<GrantedAuthority> roles = new ArrayList<>();
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
        userRoles.forEach(userRole -> {roles.add(new SimpleGrantedAuthority(userRole.getRole().getRole()));});
        return roles;
    }
}
