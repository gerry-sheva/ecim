package com.dti.ecim.discount.service;

import com.dti.ecim.discount.dao.AvailableDiscountDao;
import com.dti.ecim.discount.dto.*;
import com.dti.ecim.discount.entity.Discount;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface DiscountService {
    void createGlobalDiscount(CreateGlobalDiscountRequestDto requestDto);
    Discount createEventDiscount(CreateEventDiscountRequestDto requestDto);
    void claimDiscount(ClaimDiscountRequestDto requestDto);
    ProcessDiscountResponseDto processDiscount(ProcessDiscountRequestDto requestDto) throws BadRequestException;
    void addPoint(Long referralId);
    int retrievePoints();
    List<AvailableDiscountResponseDto> retrieveAvailableDiscounts(Long eventId);
}
