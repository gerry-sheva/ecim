package com.dti.ecim.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedeemDiscountRequestDto {
    private Long redeemedDiscountId;
    private Long eventId;
}
