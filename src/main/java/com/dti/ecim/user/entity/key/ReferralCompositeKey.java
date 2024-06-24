package com.dti.ecim.user.entity.key;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferralCompositeKey {
    private Long referralId;

    private Long referreeId;
}
