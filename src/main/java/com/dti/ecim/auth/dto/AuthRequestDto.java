package com.dti.ecim.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestDto {

    @Email
    private String email;

    @Size(min = 8)
    private String password;

    private String confirmPassword;
}
