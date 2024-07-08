package com.dti.ecim.discount.service;

import com.dti.ecim.discount.dto.*;

public interface DiscountService {
    void createGlobalDiscount(CreateGlobalDiscountRequestDto requestDto);
    void createEventDiscount(CreateEventDiscountRequestDto requestDto);
    void claimDiscount(ClaimDiscountRequestDto requestDto);
    ProcessDiscountResponseDto processDiscount(ProcessDiscountRequestDto requestDto);
    void addPoint();
}
