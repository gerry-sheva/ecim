package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.CreateEventOfferingDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventOffering;
import com.dti.ecim.event.repository.EventOfferingRepository;
import com.dti.ecim.event.service.EventOfferingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log
public class EventOfferingServiceImpl implements EventOfferingService {
    private final EventOfferingRepository eventOfferingRepository;
    private final ModelMapper modelMapper;

    @Override
    public EventOffering createEventOffering(CreateEventOfferingDto createEventOfferingDto, Event event) {
        EventOffering eventOffering =  modelMapper.map(createEventOfferingDto, EventOffering.class);
        eventOffering.setAvailability(createEventOfferingDto.getCapacity());
        eventOffering.setEvent(event);
        return eventOfferingRepository.save(eventOffering);
    }
}
