package com.dti.ecim.discount.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CreateEventDiscountRequestDto extends CreateGlobalDiscountRequestDto{
    private Long eventId;
}
