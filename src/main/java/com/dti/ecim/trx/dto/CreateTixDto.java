package com.dti.ecim.trx.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTixDto {
    private Long offeringId;
    private int quantity;
}
