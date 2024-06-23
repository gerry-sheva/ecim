package com.dti.ecim.user.attendee.referral.entity.key;


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
