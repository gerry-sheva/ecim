package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.ClaimedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimedDiscountRepository extends JpaRepository<ClaimedDiscount, Long> {
}
