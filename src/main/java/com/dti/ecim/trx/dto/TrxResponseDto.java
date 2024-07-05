package com.dti.ecim.trx.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class TrxResponseDto {
    private Long id;
    private int price;
    private Set<TixResponseDto> tixes;
}
