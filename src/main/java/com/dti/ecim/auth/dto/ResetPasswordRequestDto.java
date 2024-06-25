package com.dti.ecim.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequestDto {
    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
