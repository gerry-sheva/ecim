package com.dti.ecim.trx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTixDto {
    @Positive(message = "Offering id must be valid")
    @NotNull(message = "Offering id must not be null")
    private Long offeringId;

    @Positive(message = "Quantity must be valid")
    @NotNull(message = "Quantity must not be null")
    private int quantity;
}
