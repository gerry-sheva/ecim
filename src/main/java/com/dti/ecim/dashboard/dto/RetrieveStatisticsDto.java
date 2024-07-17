package com.dti.ecim.dashboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RetrieveStatisticsDto {
    private int count;
    private int offeringId;
    private int price;
    private int revenue;
}
