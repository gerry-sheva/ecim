package com.dti.ecim.user.attendee.referral.service;

import org.apache.coyote.BadRequestException;

public interface ReferralService {
    boolean isAlreadyExists(Long referralId, Long referreeId) throws BadRequestException;
}
