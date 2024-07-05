package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.RedeemedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedeemedDiscountRepository extends JpaRepository<RedeemedDiscount, Long> {
    Optional<RedeemedDiscount> findByAttendeeIdAndDiscountId(Long attendeeId, Long discountId);
}
