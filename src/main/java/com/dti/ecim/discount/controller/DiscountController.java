package com.dti.ecim.discount.controller;

import com.dti.ecim.discount.dto.ClaimDiscountRequestDto;
import com.dti.ecim.discount.dto.CreateEventDiscountRequestDto;
import com.dti.ecim.discount.dto.CreateGlobalDiscountRequestDto;
import com.dti.ecim.discount.dto.RedeemDiscountRequestDto;
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

    @PostMapping("/event")
    public void eventDiscount(@RequestBody CreateEventDiscountRequestDto createEventDiscountRequestDto) {
        discountService.createEventDiscount(createEventDiscountRequestDto);
    }

    @PostMapping("/redeem")
    public void redeemDiscount(@RequestBody RedeemDiscountRequestDto redeemDiscountRequestDto) {
        discountService.redeemDiscount(redeemDiscountRequestDto);
    }

    @PostMapping("/claim")
    public void claimDiscount(@RequestBody ClaimDiscountRequestDto claimDiscountRequestDto) {
        discountService.claimDiscount(claimDiscountRequestDto);
    }
}
