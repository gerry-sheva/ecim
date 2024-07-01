package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event createEvent(CreateEventDto createEventDto);
    Event findEventById(Long id);
    List<Event> findAllEvents();
}
