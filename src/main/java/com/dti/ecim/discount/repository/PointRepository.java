package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.Point;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("SELECT SUM(p.amount) FROM Point p WHERE p.attendeeId = :attendeeId AND p.expiredAt >= :threeMonthsAgo")
    int findSumAmount(@Param("attendeeId") Long attendeeId, @Param("threeMonthsAgo") Instant threeMonthsAgo);
}
