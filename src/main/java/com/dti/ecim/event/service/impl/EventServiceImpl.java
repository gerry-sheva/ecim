package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.repository.EventRepository;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.metadata.entity.Category;
import com.dti.ecim.metadata.entity.Interest;
import com.dti.ecim.metadata.service.CategoryService;
import com.dti.ecim.metadata.service.InterestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryService categoryService;
    private final InterestService interestService;

    @Override
    public Event createEvent(CreateEventDto createEventDto) {
        Category category = categoryService.findById(createEventDto.getCategoryId());
        Interest interest = interestService.findById(createEventDto.getInterestId());
        Event event = new Event();
        event.setTitle(createEventDto.getTitle());
        event.setDescription(createEventDto.getDescription());
        event.setCategory(category);
        event.setInterest(interest);
        event.setStartingDate(stringToInstant(createEventDto.getStartingDate()));
        event.setEndingDate(stringToInstant(createEventDto.getEndingDate()));
        return eventRepository.save(event);
    }

    @Override
    public Event findEventById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new DataNotFoundException("Event with id " + id + " not found");
        }
        return eventOptional.get();
    }

    @Override
    public List<Event> findAllEvents() {
        return List.of();
    }

    private Instant stringToInstant(String time) {
        String pattern = "hh:mm:ss a, EEE M/d/uuuu";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        ZoneId zoneId = ZoneId.of("Asia/Jakarta");
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zonedDateTime.toInstant();
    }
}
