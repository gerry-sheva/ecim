package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.*;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.Interest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    RetrieveEventResponseDto createEvent(CreateEventRequestDto createEventRequestDto) throws BadRequestException;
    RetrieveEventResponseDto findEventById(Long id);
    RetrieveEventResponseDto updateEvent(Long id, UpdateEventDto updateEventDto);
    Page<RetrieveEventResponseDto> displayEvents(
            Pageable pageable,
            String title,
            String category,
            String interest,
            String city,
            String state);
    EventOfferingResponseDto getEventOffering(Long eventOfferingId);
    Interest findInterestById(Long id);
    Page<RetrieveEventResponseDto> displayOrganizerEvents(Long organizerId, Pageable pageable);
    void addReview(AddReviewRequestDto requestDto);
}
