package com.dti.ecim.event.controller;

import com.dti.ecim.event.dto.AddReviewRequestDto;
import com.dti.ecim.event.dto.CreateEventRequestDto;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/event")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<?> retrieveAllEvents (
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String interest,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) boolean isValid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        var res = eventService.displayEvents(
                pageable,
                title,
                category,
                interest,
                city,
                state,
                isValid
        );
        return Response.success(HttpStatus.OK.value(), "Events retrieved successfully", res);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) throws BadRequestException {
        var res = eventService.createEvent(createEventRequestDto);
        return Response.success(HttpStatus.CREATED.value(), "Event created successfully", res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveEventDetails(@PathVariable Long id) {
        var res = eventService.findEventById(id);
        return Response.success(HttpStatus.OK.value(), "Event retrieved successfully", res);
    }

    @GetMapping("/organizer/{id}")
    public ResponseEntity<?> displayOrganizerEvents(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var res = eventService.displayOrganizerEvents(id,PageRequest.of(page, size));
        return Response.success(HttpStatus.OK.value(), "Organizer events retrieved successfully", res);
    }

    @PostMapping("/review")
    public void addReview(@RequestBody AddReviewRequestDto requestDto) {
        eventService.addReview(requestDto);
    }

    @GetMapping("/category")
    public ResponseEntity<?> retrieveCategories() {
        var res = eventService.retrieveCategories();
        return Response.success(HttpStatus.OK.value(), "Categories retrieved successfully", res);
    }

    @GetMapping("/interest")
    public ResponseEntity<?> retrieveInterests() {
        var res = eventService.retrieveInterests();
        return Response.success(HttpStatus.OK.value(), "Interests retrieved successfully", res);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findSuggestions(@RequestParam String search) {
        var res = eventService.findSuggestions(search);
        return Response.success(HttpStatus.OK.value(), "Suggestions retrieved successfully", res);
    }
}
