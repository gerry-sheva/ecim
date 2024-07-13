package com.dti.ecim.event.controller;

import com.dti.ecim.event.dto.AddReviewRequestDto;
import com.dti.ecim.event.dto.CreateEventRequestDto;
import com.dti.ecim.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<?> displayEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String interest,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
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
                state
        );
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) throws BadRequestException {
        var res = eventService.createEvent(createEventRequestDto);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> displayEvent(@PathVariable Long id) {
        var res = eventService.findEventById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/organizer/{id}")
    public ResponseEntity<?> displayOrganizerEvents(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var res = eventService.displayOrganizerEvents(id,PageRequest.of(page, size));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/review")
    public void addReview(@RequestBody AddReviewRequestDto requestDto) {
        eventService.addReview(requestDto);
    }
}
