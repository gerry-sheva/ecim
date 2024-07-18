package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.Discount;
import com.dti.ecim.discount.entity.EventDiscountDao;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCode(String code);

    @Query(value = "SELECT event_id from discount as d where d.id = :id", nativeQuery = true)
    EventDiscountDao retrieveEventDiscount(@Param("id") Long id);
}
