package com.dti.ecim.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotBlank
    private String street1;
    @NotBlank
    private String street2;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
}
