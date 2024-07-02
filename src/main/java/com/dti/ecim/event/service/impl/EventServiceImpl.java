package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.dto.CreateEventOfferingDto;
import com.dti.ecim.event.dto.RetrieveEventDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventLocation;
import com.dti.ecim.event.repository.EventLocationRepository;
import com.dti.ecim.event.repository.EventRepository;
import com.dti.ecim.event.repository.EventSpecifications;
import com.dti.ecim.event.service.EventOfferingService;
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
import org.springframework.transaction.annotation.Transactional;

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
    private final EventOfferingService eventOfferingService;
    private final EventLocationRepository eventLocationRepository;
    private final CategoryService categoryService;
    private final InterestService interestService;

    @Override
    @Transactional
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
        Event createdEvent = eventRepository.save(event);

        log.info("Created event: " + createdEvent.getId());

        EventLocation eventLocation = new EventLocation();
        eventLocation.setEvent(createdEvent);
        eventLocation.setStreet1(createEventDto.getStreet1());
        eventLocation.setStreet2(createEventDto.getStreet2());
        eventLocation.setCity(createEventDto.getCity());
        eventLocation.setState(createEventDto.getState());
        eventLocationRepository.save(eventLocation);

        List<CreateEventOfferingDto> createEventOfferingDtoList = createEventDto.getOfferings();
        for (CreateEventOfferingDto createEventOfferingDto : createEventOfferingDtoList) {
            eventOfferingService.createEventOffering(createEventOfferingDto, createdEvent);
        }

        return createdEvent;
    }

    @Override
    public RetrieveEventDto findEventById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new DataNotFoundException("Event with id " + id + " not found");
        }
        Event event = eventOptional.get();
        RetrieveEventDto retrieveEventDto = new RetrieveEventDto();
        retrieveEventDto.setTitle(event.getTitle());
        retrieveEventDto.setDescription(event.getDescription());
        retrieveEventDto.setStartingDate(event.getStartingDate());
        retrieveEventDto.setEndingDate(event.getEndingDate());
        retrieveEventDto.setCity(event.getLocation().getCity());
        retrieveEventDto.setState(event.getLocation().getState());
        retrieveEventDto.setCategory(event.getCategory().getName());
        retrieveEventDto.setInterest(event.getInterest().getName());
        return retrieveEventDto;
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
//        Category category = categoryService.findById(updateEventDto.getCategoryId());
//        Interest interest = interestService.findById(updateEventDto.getInterestId());
//        Event event = findEventById(id);
//        event.setTitle(updateEventDto.getTitle());
//        event.setDescription(updateEventDto.getDescription());
//        event.setCategory(category);
//        event.setInterest(interest);
//        event.setStartingDate(stringToInstant(updateEventDto.getStartingDate()));
//        event.setEndingDate(stringToInstant(updateEventDto.getEndingDate()));
//        return eventRepository.save(event);
        return null;
    }

    @Override
    public Page<Event> displayEvents(Pageable pageable, String title, String category, String interest, String city, String state) {
        Specification<Event> specification = Specification.where(EventSpecifications.byTitle(title))
                .and(EventSpecifications.byCategory(category))
                .and(EventSpecifications.byInterest(interest))
                .and(EventSpecifications.byCity(city))
                .and(EventSpecifications.byState(state));
        return eventRepository.findAll(specification, pageable);
    }
}
