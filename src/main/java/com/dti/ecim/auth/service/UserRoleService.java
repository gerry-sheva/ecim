package com.dti.ecim.auth.service;

import com.dti.ecim.auth.dto.AddUserRoleDto;
import com.dti.ecim.auth.entity.UserRole;

public interface UserRoleService {
    void addUserRole(AddUserRoleDto addUserRoleDto);
}
