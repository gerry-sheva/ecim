package com.dti.ecim.discount.service;

import com.dti.ecim.discount.dto.*;
import org.apache.coyote.BadRequestException;

public interface DiscountService {
    void createGlobalDiscount(CreateGlobalDiscountRequestDto requestDto);
    void createEventDiscount(CreateEventDiscountRequestDto requestDto);
    void claimDiscount(ClaimDiscountRequestDto requestDto);
    ProcessDiscountResponseDto processDiscount(ProcessDiscountRequestDto requestDto) throws BadRequestException;
    void addPoint(Long referralId);
}
