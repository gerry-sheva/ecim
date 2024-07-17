package com.dti.ecim.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDiscountResponseDto {
    private Long id;
    private String name;
    private String description;
    private int amountFlat;
    private int amountPercent;
}
