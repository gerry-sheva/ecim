package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.RedeemedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedeemedDiscountRepository extends JpaRepository<RedeemedDiscount, Long> {
}
