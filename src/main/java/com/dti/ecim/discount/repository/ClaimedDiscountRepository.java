package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.ClaimedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimedDiscountRepository extends JpaRepository<ClaimedDiscount, Long> {
    Optional<ClaimedDiscount> findByAttendeeIdAndDiscountId(Long attendeeId, Long discountId);
}
