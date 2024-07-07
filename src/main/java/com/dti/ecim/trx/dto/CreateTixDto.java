package com.dti.ecim.trx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTixDto {
    private Long offeringId;
    private int quantity;
}
