package com.dti.ecim.discount.service;

import com.dti.ecim.discount.entity.Discount;
import com.dti.ecim.discount.entity.EventDiscount;
import com.dti.ecim.discount.entity.GlobalDiscount;
import com.dti.ecim.discount.entity.RedeemedDiscount;

public interface DiscountService {
    void createGlobalEvent(GlobalDiscount globalDiscount);
    void createEventDiscount(EventDiscount eventDiscount);
    void redeemDiscount(Discount discount);
    void claimDiscount(RedeemedDiscount redeemedDiscount);
}
