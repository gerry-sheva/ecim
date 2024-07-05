package com.dti.ecim.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class CreateEventOfferingDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @PositiveOrZero
    private int price;

    @Positive
    private int capacity;
}
