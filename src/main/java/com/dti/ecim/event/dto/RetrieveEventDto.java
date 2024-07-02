package com.dti.ecim.event.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class RetrieveEventDto {
    private String title;

    private String description;

    private Instant startingDate;

    private Instant endingDate;

    private String city;

    private String state;

    private String category;

    private String interest;
}
