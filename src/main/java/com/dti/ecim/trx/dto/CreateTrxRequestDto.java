package com.dti.ecim.trx.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CreateTrxRequestDto {
    private Set<CreateTixDto> tixes;
}
