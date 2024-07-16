package com.dti.ecim.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDiscountRequestDto {
    private Long discountId;
    private Long eventId;
    private boolean isUsingPoint;
    private int totalPrice;
}
