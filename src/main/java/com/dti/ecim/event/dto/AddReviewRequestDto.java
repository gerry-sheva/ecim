package com.dti.ecim.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddReviewRequestDto {
    private Long eventId;
    private int rating;
    private String review;
}
