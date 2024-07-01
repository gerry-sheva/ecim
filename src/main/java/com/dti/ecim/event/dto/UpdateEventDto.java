package com.dti.ecim.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEventDto {
    private String title;
    private String description;
    private String startingDate;
    private String endingDate;
    private Long categoryId;
    private Long interestId;
}
