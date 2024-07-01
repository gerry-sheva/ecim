package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.repository.EventRepository;
import com.dti.ecim.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(CreateEventDto createEventDto) {
        return null;
    }

    @Override
    public Event findEventById(Long id) {
        return null;
    }

    @Override
    public List<Event> findAllEvents() {
        return List.of();
    }
}
