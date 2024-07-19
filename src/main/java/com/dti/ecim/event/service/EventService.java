package com.dti.ecim.event.service;

import com.dti.ecim.event.dto.*;
import com.dti.ecim.event.entity.EventOffering;
import com.dti.ecim.event.entity.Interest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    RetrieveEventResponseDto createEvent(CreateEventRequestDto createEventRequestDto) throws BadRequestException;
    RetrieveEventResponseDto findEventById(Long id);
    RetrieveEventResponseDto updateEvent(Long id, UpdateEventDto updateEventDto);
    Page<RetrieveEventWithoutOfferingResponseDto> displayEvents(
            Pageable pageable,
            String title,
            String category,
            String interest,
            String city,
            String state);
    EventOffering getEventOffering(Long eventOfferingId);
    Interest findInterestById(Long id);
    Page<RetrieveEventResponseDto> displayOrganizerEvents(Long organizerId, Pageable pageable);
    void addReview(AddReviewRequestDto requestDto);
    void updateOffering(EventOffering eventOffering);
    List<RetrieveEventResponseDto.CategoryDto> retrieveCategories();
    List<RetrieveEventResponseDto.InterestDto> retrieveInterests();
    List<FindSuggestionsResponseDto> findSuggestions(String search);
}
