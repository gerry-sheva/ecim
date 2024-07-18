package com.dti.ecim.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class RetrieveEventWithoutOfferingResponseDto {
    private String title;

    private String description;

    private Instant startingDate;

    private Instant endingDate;

    private int price;

    private String imageSrc;

    private RetrieveEventResponseDto.OrganizerDto organizer;

    private RetrieveEventResponseDto.LocationDto location;

    private RetrieveEventResponseDto.CategoryDto category;

    private RetrieveEventResponseDto.InterestDto interest;
}
