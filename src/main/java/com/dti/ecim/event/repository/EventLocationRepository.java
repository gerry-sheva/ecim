package com.dti.ecim.event.repository;

import com.dti.ecim.event.entity.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
}
