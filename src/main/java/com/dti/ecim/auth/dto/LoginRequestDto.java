package com.dti.ecim.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @Email(message = "Must be valid email address")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Size(min = 8, message = "Password must not be less than 8 characters")
    @NotBlank(message = "Password must not be blank")
    private String password;
}
