package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.CreateEventOfferingDto;
import com.dti.ecim.event.dto.EventOfferingResponseDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventOffering;

public interface EventOfferingService {
    EventOfferingResponseDto createEventOffering(CreateEventOfferingDto createEventOfferingDto, Event event);
    EventOfferingResponseDto getEventOffering(Long eventOfferingId);
}
