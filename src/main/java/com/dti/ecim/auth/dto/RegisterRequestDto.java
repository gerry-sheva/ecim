package com.dti.ecim.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDto {

    @Email(message = "Must be valid email address")
    @NotBlank(message = "Email must not be null")
    private String email;

    @Size(min = 8, message = "Password must not be less than 8 characters")
    @NotBlank(message = "Password must not be null")
    private String password;

    @Size(min = 8, message = "Password must not be less than 8 characters")
    @NotBlank(message = "Password must not be null")
    private String confirmPassword;
}
