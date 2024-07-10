package com.dti.ecim.event.repository;

import com.dti.ecim.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Page<Event> findAllByOrganizerIdOrderByEndingDate(Long organizerId, Pageable pageable);
}
