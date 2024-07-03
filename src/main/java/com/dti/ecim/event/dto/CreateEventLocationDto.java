package com.dti.ecim.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEventLocationDto {
    @NotBlank
    private String street1;

    @NotBlank
    private String street2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    public CreateEventLocationDto() {
        this.street1 = "";
        this.street2 = "";
        this.city = "";
        this.state = "";
    }
}
