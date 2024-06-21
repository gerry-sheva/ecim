package com.dti.ecim.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDto {

    @Email
    private String email;

    @Size(min = 8)
    private String password;

    private String confirmPassword;

    private String referralCode;
}
