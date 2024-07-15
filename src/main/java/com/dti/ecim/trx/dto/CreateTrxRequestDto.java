package com.dti.ecim.trx.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CreateTrxRequestDto {
    @Positive(message = "Event id must be valid")
    @NotNull(message = "Event id can't be null")
    private Long eventId;

    @Positive(message = "Organizer id must be valid")
    @NotNull(message = "Organzier id can't be null")
    private Long organizerId;

    private Long discountId;

    private boolean point;

    @Size(min = 1, message = "Tickets can't be empty")
    @NotNull(message = "Tickets can't be null")
    @Valid
    private Set<CreateTixDto> tixes;
}
