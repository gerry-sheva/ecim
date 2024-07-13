package com.dti.ecim.auth.dto;

import com.dti.ecim.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIdResponseDto {
    private Long id;
    private String email;
    private Role role;
}
