package com.dti.ecim.user.repository;

import com.dti.ecim.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
