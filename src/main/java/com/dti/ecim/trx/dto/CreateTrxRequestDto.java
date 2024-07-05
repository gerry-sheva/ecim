package com.dti.ecim.trx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CreateTrxRequestDto {
    @Positive
    @NotNull
    private Long eventId;

    private Long discountId;

    @NotNull
    private Set<CreateTixDto> tixes;
}
