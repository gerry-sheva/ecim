package com.dti.ecim.trx.dto;

import com.dti.ecim.event.dto.EventOfferingResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TixResponseDto {
    private String code;
    private EventOfferingResponseDto eventOffering;
}
