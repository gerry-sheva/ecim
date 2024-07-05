package com.dti.ecim.discount.service;

import com.dti.ecim.discount.dto.CreateEventDiscountRequestDto;
import com.dti.ecim.discount.dto.CreateGlobalDiscountRequestDto;
import com.dti.ecim.discount.dto.RedeemDiscountRequestDto;
import com.dti.ecim.discount.entity.Discount;
import com.dti.ecim.discount.entity.EventDiscount;
import com.dti.ecim.discount.entity.GlobalDiscount;
import com.dti.ecim.discount.entity.RedeemedDiscount;

public interface DiscountService {
    void createGlobalDiscount(CreateGlobalDiscountRequestDto requestDto);
    void createEventDiscount(CreateEventDiscountRequestDto requestDto);
    void redeemDiscount(RedeemDiscountRequestDto requestDto);
    void claimDiscount(RedeemedDiscount redeemedDiscount);
}
