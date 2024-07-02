package com.dti.ecim.event.repository;

import com.dti.ecim.event.entity.EventOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOfferingRepository extends JpaRepository<EventOffering, Long> {
}
