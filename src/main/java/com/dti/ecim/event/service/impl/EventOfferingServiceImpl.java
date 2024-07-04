package com.dti.ecim.event.service.impl;

import com.dti.ecim.event.dto.CreateEventOfferingDto;
import com.dti.ecim.event.dto.EventOfferingResponseDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.entity.EventOffering;
import com.dti.ecim.event.repository.EventOfferingRepository;
import com.dti.ecim.event.service.EventOfferingService;
import com.dti.ecim.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class EventOfferingServiceImpl implements EventOfferingService {
    private final EventOfferingRepository eventOfferingRepository;
    private final ModelMapper modelMapper;

    @Override
    public EventOfferingResponseDto createEventOffering(CreateEventOfferingDto createEventOfferingDto, Event event) {
        EventOffering eventOffering =  modelMapper.map(createEventOfferingDto, EventOffering.class);
        eventOffering.setAvailability(createEventOfferingDto.getCapacity());
        EventOffering offering = eventOfferingRepository.save(eventOffering);
        return modelMapper.map(offering, EventOfferingResponseDto.class);
    }

    @Override
    public EventOfferingResponseDto getEventOffering(Long eventOfferingId) {
        Optional<EventOffering> eventOfferingOptional = eventOfferingRepository.findById(eventOfferingId);
        if (eventOfferingOptional.isEmpty()) {
            throw new DataNotFoundException("Event offering with id " + eventOfferingId + " not found");
        }
        EventOffering eventOffering = eventOfferingOptional.get();
        EventOfferingResponseDto eventOfferingResponseDto = modelMapper.map(eventOffering, EventOfferingResponseDto.class);
        return eventOfferingResponseDto;
    }
}
