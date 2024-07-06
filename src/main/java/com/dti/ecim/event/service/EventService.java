package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.CreateEventRequestDto;
import com.dti.ecim.event.dto.EventOfferingResponseDto;
import com.dti.ecim.event.dto.RetrieveEventResponseDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.Interest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Event createEvent(CreateEventRequestDto createEventRequestDto);
    RetrieveEventResponseDto findEventById(Long id);
    Event updateEvent(Long id, UpdateEventDto updateEventDto);
    Page<RetrieveEventResponseDto> displayEvents(
            Pageable pageable,
            String title,
            String category,
            String interest,
            String city,
            String state);
    Event dumpEvent(Long id);
    EventOfferingResponseDto getEventOffering(Long eventOfferingId);
    Interest findInterestById(Long id);
}
