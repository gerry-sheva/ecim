package com.dti.ecim.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateEventOfferingDto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @PositiveOrZero
    private Long price;

    @Positive
    private int capacity;
}
