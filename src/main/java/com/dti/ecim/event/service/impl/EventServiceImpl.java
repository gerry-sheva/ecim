package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.repository.EventRepository;
import com.dti.ecim.event.repository.EventSpecifications;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.metadata.entity.Category;
import com.dti.ecim.metadata.entity.Interest;
import com.dti.ecim.metadata.service.CategoryService;
import com.dti.ecim.metadata.service.InterestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    @Override
    public Event updateEvent(Long id, UpdateEventDto updateEventDto) {
        Category category = categoryService.findById(updateEventDto.getCategoryId());
        Interest interest = interestService.findById(updateEventDto.getInterestId());
        Event event = findEventById(id);
        event.setTitle(updateEventDto.getTitle());
        event.setDescription(updateEventDto.getDescription());
        event.setCategory(category);
        event.setInterest(interest);
        event.setStartingDate(stringToInstant(updateEventDto.getStartingDate()));
        event.setEndingDate(stringToInstant(updateEventDto.getEndingDate()));
        return eventRepository.save(event);
    }

    @Override
    public Page<Event> displayEvents(Pageable pageable, String title, String category, String interest) {
//        Long categoryId = null;
//        Long interestId = null;
//        if (category != null) {
//            Category cat = categoryService.findByName(category);
//            categoryId = cat.getId();
//        }
//        if (interest != null) {
//            Interest interestI = interestService.findByName(interest);
//            interestId = interestI.getId();
//        }
        Specification<Event> specification = Specification.where(EventSpecifications.byTitle(title))
                .and(EventSpecifications.byCategory(category))
                .and(EventSpecifications.byInterest(interest));
        Page<Event> events = eventRepository.findAll(specification, pageable);
        return events;
    }
}
