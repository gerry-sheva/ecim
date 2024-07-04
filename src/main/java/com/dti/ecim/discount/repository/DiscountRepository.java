package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
