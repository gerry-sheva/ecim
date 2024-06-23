package com.dti.ecim.auth.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRoleDto {
    @Positive
    private Long userId;

    @Positive
    private Long roleId;
}
