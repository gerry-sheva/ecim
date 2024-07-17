package com.dti.ecim.discount.controller;

import com.dti.ecim.discount.dto.CreateEventDiscountRequestDto;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/event")
    public ResponseEntity<?> eventDiscount(@RequestBody CreateEventDiscountRequestDto createEventDiscountRequestDto) {
        var res =  discountService.createEventDiscount(createEventDiscountRequestDto);
        return Response.success(HttpStatus.CREATED.value(), "Event discount successfully created", res);
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableDiscounts(@RequestParam Long eventId) {
        var res = discountService.retrieveAvailableDiscounts(eventId);
        return Response.success(HttpStatus.OK.value(), "Successfully retrieved available discount", res);
    }
}
