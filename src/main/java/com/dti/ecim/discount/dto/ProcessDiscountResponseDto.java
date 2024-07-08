package com.dti.ecim.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDiscountResponseDto {
    private int discountValue;
    private int finalPrice;
}
