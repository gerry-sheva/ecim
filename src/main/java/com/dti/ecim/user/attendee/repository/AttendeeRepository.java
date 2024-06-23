package com.dti.ecim.user.attendee.repository;

import com.dti.ecim.user.attendee.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Optional<Attendee> findByRefCode(String refCode);
}
