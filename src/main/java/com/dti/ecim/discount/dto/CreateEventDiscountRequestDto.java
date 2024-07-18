package com.dti.ecim.discount.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CreateEventDiscountRequestDto extends CreateGlobalDiscountRequestDto{
    @Positive(message = "Event id must be valid")
    @NotNull(message = "Event id must not be null")
    private Long eventId;
}
