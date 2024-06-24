package com.dti.ecim.user.repository;

import com.dti.ecim.user.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Optional<Attendee> findByRefCode(String refCode);
}
