package com.dti.ecim.auth.service;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface UserRoleService {
    void addUserRole(AddUserRoleDto addUserRoleDto);
    List<GrantedAuthority> retrieveRoles(Long userId);
    boolean isRoleExist(Long userId);
}
