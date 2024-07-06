package com.dti.ecim.event.controller;

import com.dti.ecim.event.dto.CreateEventRequestDto;
import com.dti.ecim.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDto createEventRequestDto) {
        var res = eventService.createEvent(createEventRequestDto);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> displayEvent(@PathVariable Long id) {
        var res = eventService.findEventById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/dump/{id}")
    public ResponseEntity<?> dumpEvent(@PathVariable Long id) {
        var res = eventService.dumpEvent(id);
        return ResponseEntity.ok(res);
    }
}
