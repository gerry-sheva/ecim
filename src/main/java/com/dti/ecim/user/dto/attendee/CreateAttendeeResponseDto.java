package com.dti.ecim.user.dto.attendee;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttendeeResponseDto {
    private String fname;

    private String lname;

    private LocalDate dob;

    private String contact;

    private String referralCode;

    private String role;

    private String token;
}
