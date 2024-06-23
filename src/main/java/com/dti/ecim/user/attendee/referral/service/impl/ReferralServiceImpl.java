package com.dti.ecim.user.attendee.referral.service.impl;

import com.dti.ecim.user.attendee.referral.entity.Referral;
import com.dti.ecim.user.attendee.referral.repository.ReferralRepository;
import com.dti.ecim.user.attendee.referral.service.ReferralService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository referralRepository;

    @Override
    public boolean isAlreadyExists(Long referralId, Long referreeId) throws BadRequestException {
        Optional<Referral> referral = referralRepository.findByReferralIdAndReferreeId(referralId, referreeId);
        if (referral.isPresent()) {
            throw new BadRequestException("Referral relationship already exists!");
        }
        return false;
    }
}
