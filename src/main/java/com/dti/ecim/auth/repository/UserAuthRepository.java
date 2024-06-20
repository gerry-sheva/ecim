package com.dti.ecim.auth.repository;

import com.dti.ecim.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
}
