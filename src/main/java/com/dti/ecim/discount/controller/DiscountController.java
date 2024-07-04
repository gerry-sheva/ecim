package com.dti.ecim.discount.controller;

import com.dti.ecim.discount.dto.CreateGlobalDiscountRequestDto;
import com.dti.ecim.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/global")
    public void globalDiscount(@RequestBody CreateGlobalDiscountRequestDto createGlobalDiscountRequestDto) {
        discountService.createGlobalDiscount(createGlobalDiscountRequestDto);
    }
}
