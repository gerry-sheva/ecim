package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.CreateEventDto;
import com.dti.ecim.event.dto.EventOfferingResponseDto;
import com.dti.ecim.event.dto.RetrieveEventDto;
import com.dti.ecim.event.dto.UpdateEventDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.Interest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Event createEvent(CreateEventDto createEventDto);
    RetrieveEventDto findEventById(Long id);
    Event updateEvent(Long id, UpdateEventDto updateEventDto);
    Page<RetrieveEventDto> displayEvents(
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
