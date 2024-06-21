package com.dti.ecim.user.attendee.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAttendeeDto {

    @NotBlank
    private String fname;

    @NotBlank
    private String lname;

    @NotBlank
    private String dob;

    @NotBlank
    private String contact;
}
