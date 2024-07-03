package com.dti.ecim.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateEventDto {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String startingDate;

    @NotBlank
    private String endingDate;

    @NotBlank
    private Long categoryId;

    @NotBlank
    private Long interestId;

    @NotNull
    private CreateEventLocationDto location;

    @NotNull
    private List<CreateEventOfferingDto> offerings;
}
