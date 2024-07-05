package com.dti.ecim.user.dto.attendee;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAttendeeRequestDto {
    @NotBlank
    private String fname;

    @NotBlank
    private String lname;

    @NotBlank
    private String dob;

    @NotBlank
    private String contact;

    private String referralCode;
}
