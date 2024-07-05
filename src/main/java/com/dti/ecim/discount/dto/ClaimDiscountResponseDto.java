package com.dti.ecim.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClaimDiscountResponseDto {
    private int amountFlat;
    private int amountPercent;
}
