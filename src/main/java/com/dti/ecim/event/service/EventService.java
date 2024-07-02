package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event createEvent(CreateEventDto createEventDto);
    Event findEventById(Long id);
    List<Event> findAllEvents();
    Event updateEvent(Long id, UpdateEventDto updateEventDto);
    Page<Event> displayEvents(
            Pageable pageable,
            String title,
            String category,
            String interest,
            String city,
            String state);
}
