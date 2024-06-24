package com.dti.ecim.user.repository;

import com.dti.ecim.user.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
    Optional<Referral> findByReferralIdAndReferreeId(Long referralId, Long referreeId);
}
