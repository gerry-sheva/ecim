package com.dti.ecim.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventOfferingResponseDto {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int capacity;
    private int availability;
}
