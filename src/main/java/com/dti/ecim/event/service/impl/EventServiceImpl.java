package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.*;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventLocation;
import com.dti.ecim.event.entity.EventOffering;
import com.dti.ecim.event.repository.*;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.event.entity.Interest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
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
    private final EventOfferingRepository eventOfferingRepository;
    private final EventLocationRepository eventLocationRepository;
    private final InterestRepository interestRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Event createEvent(CreateEventDto createEventDto) {
        Interest interest = findInterestById(createEventDto.getInterestId());
        Event event = new Event();
        event.setTitle(createEventDto.getTitle());
        event.setDescription(createEventDto.getDescription());
        event.setInterest(interest);
        event.setStartingDate(stringToInstant(createEventDto.getStartingDate()));
        event.setEndingDate(stringToInstant(createEventDto.getEndingDate()));

        List<CreateEventOfferingDto> createEventOfferingDtoList = createEventDto.getOfferings();
        for (CreateEventOfferingDto createEventOfferingDto : createEventOfferingDtoList) {
            EventOffering eventOffering = modelMapper.map(createEventOfferingDto, EventOffering.class);
            eventOffering.setAvailability(createEventOfferingDto.getCapacity());
            event.addOffering(eventOffering);
        }
        EventLocation eventLocation = modelMapper.map(createEventDto.getLocation(), EventLocation.class);
        event.addLocation(eventLocation);
        Event createdEvent = eventRepository.save(event);

        log.info("Created event: " + createdEvent.getId());

        return createdEvent;
    }

    @Override
    public RetrieveEventDto findEventById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new DataNotFoundException("Event with id " + id + " not found");
        }
        Event event = eventOptional.get();
        return modelMapper.map(event, RetrieveEventDto.class);
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
        Interest interest = findInterestById(updateEventDto.getInterestId());
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new DataNotFoundException("Event with id " + id + " not found");
        }
        Event event = eventOptional.get();
        event.setTitle(updateEventDto.getTitle());
        event.setDescription(updateEventDto.getDescription());
        event.setInterest(interest);
        event.setStartingDate(stringToInstant(updateEventDto.getStartingDate()));
        event.setEndingDate(stringToInstant(updateEventDto.getEndingDate()));
        return eventRepository.save(event);
    }

    @Override
    public Page<RetrieveEventDto> displayEvents(Pageable pageable, String title, String category, String interest, String city, String state) {
        Specification<Event> specification = Specification.where(EventSpecifications.byTitle(title))
                .and(EventSpecifications.byCategory(category))
                .and(EventSpecifications.byInterest(interest))
                .and(EventSpecifications.byCity(city))
                .and(EventSpecifications.byState(state));
        Page<Event> events = eventRepository.findAll(specification, pageable);
        return events.map(event -> modelMapper.map(event, RetrieveEventDto.class));
    }

    @Override
    public Event dumpEvent(Long id) {

        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public EventOfferingResponseDto getEventOffering(Long eventOfferingId) {
        Optional<EventOffering> eventOfferingOptional = eventOfferingRepository.findById(eventOfferingId);
        if (eventOfferingOptional.isEmpty()) {
            throw new DataNotFoundException("Event offering with id " + eventOfferingId + " not found");
        }
        EventOffering eventOffering = eventOfferingOptional.get();
        return modelMapper.map(eventOffering, EventOfferingResponseDto.class);
    }

    @Override
    public Interest findInterestById(Long id) {
        Optional<Interest> interestOptional = interestRepository.findById(id);
        if (interestOptional.isEmpty()) {
            throw new DataNotFoundException("Interest with id " + id + " not found");
        }
        return interestOptional.get();
    }
}
