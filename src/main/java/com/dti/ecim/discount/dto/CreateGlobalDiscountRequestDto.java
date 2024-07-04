package com.dti.ecim.discount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CreateGlobalDiscountRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Long amount_flat;

    private int amount_percent;

    @NotBlank
    private String code;

    @Positive
    private int expiresInDays;

    @NotBlank
    private String expiredAt;
}
