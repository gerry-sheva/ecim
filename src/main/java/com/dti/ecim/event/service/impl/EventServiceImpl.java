package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.dto.CreateEventOfferingDto;
import com.dti.ecim.event.dto.RetrieveEventDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventLocation;
import com.dti.ecim.event.entity.EventOffering;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Log
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventOfferingService eventOfferingService;
    private final EventLocationRepository eventLocationRepository;
    private final CategoryService categoryService;
    private final InterestService interestService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Event createEvent(CreateEventDto createEventDto) {
        Interest interest = interestService.findById(createEventDto.getInterestId());
        Event event = new Event();
        event.setTitle(createEventDto.getTitle());
        event.setDescription(createEventDto.getDescription());
        event.setInterest(interest);
        event.setStartingDate(stringToInstant(createEventDto.getStartingDate()));
        event.setEndingDate(stringToInstant(createEventDto.getEndingDate()));

        List<CreateEventOfferingDto> createEventOfferingDtoList = createEventDto.getOfferings();
        for (CreateEventOfferingDto createEventOfferingDto : createEventOfferingDtoList) {
//            eventOfferingService.createEventOffering(createEventOfferingDto, createdEvent);
            EventOffering eventOffering = modelMapper.map(createEventOfferingDto, EventOffering.class);
            eventOffering.setAvailability(createEventOfferingDto.getCapacity());
            event.addOffering(eventOffering);
        }
        Event createdEvent = eventRepository.save(event);

        log.info("Created event: " + createdEvent.getId());

        EventLocation eventLocation = modelMapper.map(createEventDto.getLocation(), EventLocation.class);
        eventLocation.setEvent(createdEvent);
        eventLocationRepository.save(eventLocation);

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
        Interest interest = interestService.findById(updateEventDto.getInterestId());
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
}
