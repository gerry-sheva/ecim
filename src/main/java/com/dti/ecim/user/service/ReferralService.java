package com.dti.ecim.user.service;

import org.apache.coyote.BadRequestException;

public interface ReferralService {
    boolean isAlreadyExists(Long referralId, Long referreeId) throws BadRequestException;
}
