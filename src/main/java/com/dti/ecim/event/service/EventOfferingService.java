package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.CreateEventOfferingDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventOffering;

public interface EventOfferingService {
    EventOffering createEventOffering(CreateEventOfferingDto createEventOfferingDto, Event event);
}
