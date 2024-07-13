package com.dti.ecim.event.service.impl;

import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.event.dto.*;
import com.dti.ecim.event.entity.*;
import com.dti.ecim.event.exceptions.InvalidDateException;
import com.dti.ecim.event.repository.*;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dti.ecim.event.helper.EventHelper.stringToInstant;

@RequiredArgsConstructor
@Service
@Log
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventOfferingRepository eventOfferingRepository;
    private final InterestRepository interestRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private final AuthService authService;

    @Override
    @Transactional
    public RetrieveEventResponseDto createEvent(CreateEventRequestDto createEventRequestDto) throws BadRequestException {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Instant start = stringToInstant(createEventRequestDto.getStartingDate());
        Instant end = stringToInstant(createEventRequestDto.getEndingDate());

        if (end.isBefore(start)) {
            throw new InvalidDateException("Start date cannot be after end date");
        }
        if (end.isBefore(Instant.now())) {
            throw new InvalidDateException("End date cannot be in the past");
        }

        Category category = findCategoryById(createEventRequestDto.getCategoryId());
        Interest interest = findInterestById(createEventRequestDto.getInterestId());

        if (!Objects.equals(interest.getCategory().getId(), category.getId())) {
            throw new BadRequestException("Category id does not match interest id");
        }

        Event event = new Event();
        event.setOrganizerId(userIdResponseDto.getId());
        event.setTitle(createEventRequestDto.getTitle());
        event.setDescription(createEventRequestDto.getDescription());
        event.setCategory(category);
        event.setInterest(interest);
        event.setStartingDate(start);
        event.setEndingDate(end);

        List<CreateEventRequestDto.CreateEventOfferingDto> createEventOfferingDtoList = createEventRequestDto.getOfferings();
        if (createEventOfferingDtoList == null || createEventOfferingDtoList.isEmpty()) {
            throw new BadRequestException("No offerings provided");
        }
        for (CreateEventRequestDto.CreateEventOfferingDto createEventOfferingDto : createEventOfferingDtoList) {
            EventOffering eventOffering = modelMapper.map(createEventOfferingDto, EventOffering.class);
            eventOffering.setAvailability(createEventOfferingDto.getCapacity());
            event.addOffering(eventOffering);
        }
        EventLocation eventLocation = modelMapper.map(createEventRequestDto.getLocation(), EventLocation.class);
        event.addLocation(eventLocation);
        Event createdEvent = eventRepository.save(event);

        return modelMapper.map(createdEvent, RetrieveEventResponseDto.class);
    }

    @Override
    public RetrieveEventResponseDto findEventById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            throw new DataNotFoundException("Event with id " + id + " not found");
        }
        Event event = eventOptional.get();
        return modelMapper.map(event, RetrieveEventResponseDto.class);
    }

    @Override
    public RetrieveEventResponseDto updateEvent(Long id, UpdateEventDto updateEventDto) {
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
        return modelMapper.map(eventRepository.save(event), RetrieveEventResponseDto.class);
    }

    @Override
    public Page<RetrieveEventResponseDto> displayEvents(Pageable pageable, String title, String category, String interest, String city, String state) {
        Specification<Event> specification = Specification.where(EventSpecifications.byTitle(title))
                .and(EventSpecifications.byCategory(category))
                .and(EventSpecifications.byInterest(interest))
                .and(EventSpecifications.byCity(city))
                .and(EventSpecifications.byState(state));
        Page<Event> events = eventRepository.findAll(specification, pageable);
        return events.map(event -> modelMapper.map(event, RetrieveEventResponseDto.class));
    }

    @Override
    public Page<RetrieveEventResponseDto> displayOrganizerEvents(Long organizerId, Pageable pageable) {
        Page<Event> events = eventRepository.findAllByOrganizerIdOrderByEndingDate(organizerId, pageable);
        return events.map(event -> modelMapper.map(event, RetrieveEventResponseDto.class));
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

    private Category findCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new DataNotFoundException("Category with id " + id + " not found");
        }
        return optionalCategory.get();
    }
}
