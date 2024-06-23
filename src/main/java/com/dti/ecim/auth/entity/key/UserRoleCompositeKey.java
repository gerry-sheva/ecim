package com.dti.ecim.auth.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleCompositeKey {
    private Long userId;
    private Long roleId;
}
