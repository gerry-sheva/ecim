package com.dti.ecim.auth.repository;

import com.dti.ecim.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
