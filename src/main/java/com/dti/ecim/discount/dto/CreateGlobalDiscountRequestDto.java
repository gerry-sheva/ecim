package com.dti.ecim.discount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateGlobalDiscountRequestDto {
    @NotBlank(message = "Name must not be null")
    private String name;

    @NotBlank(message = "Description must not be null")
    private String description;

    private int amountFlat;

    private int amountPercent;

    @NotBlank(message = "Code must not be null")
    private String code;

    @Positive(message = "Expires in days must not be null")
    private int expiresInDays;

    @NotBlank(message = "Expired at must not be null")
    private String expiredAt;
}
