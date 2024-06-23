package com.dti.ecim.auth.repository;

import com.dti.ecim.auth.entity.UserRole;
import com.dti.ecim.auth.entity.key.UserRoleCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleCompositeKey> {
}
