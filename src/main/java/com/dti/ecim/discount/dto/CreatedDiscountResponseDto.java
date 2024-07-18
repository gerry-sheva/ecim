package com.dti.ecim.discount.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatedDiscountResponseDto {
    private String name;

    private String description;

    private int amountFlat;

    private int amountPercent;

    private String code;

    private int expiresInDays;

    private String expiredAt;
}
