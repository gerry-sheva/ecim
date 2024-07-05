package com.dti.ecim.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIdResponseDto {
    private Long id;
    private String email;
}
