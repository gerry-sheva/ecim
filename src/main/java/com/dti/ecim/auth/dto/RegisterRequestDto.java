package com.dti.ecim.auth.dto;

import com.dti.ecim.auth.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Size(min = 8)
    private String confirmPassword;

    @NotNull
    private Role role;
}
