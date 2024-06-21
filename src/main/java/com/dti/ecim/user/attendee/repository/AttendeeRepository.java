package com.dti.ecim.user.attendee.repository;

import com.dti.ecim.user.attendee.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
