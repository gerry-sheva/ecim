package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.entity.Point;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("SELECT SUM(p.amount) FROM Point p WHERE p.attendeeId = :attendeeId AND p.expiredAt >= :threeMonthsAgo")
    int findSumAmount(@Param("attendeeId") Long attendeeId, @Param("threeMonthsAgo") Instant threeMonthsAgo);

    @Query("SELECT COALESCE(SUM(pt.amount), 0) FROM Point pt " +
            "WHERE pt.attendeeId = :attendeeId AND pt.expiredAt > :now AND pt.createdAt > :last")
    int getCurrentPoints(@Param("attendeeId") Long attendeeId, @Param("now") Instant now, @Param("last") Instant last);

    @Query("SELECT MAX(pt.expiredAt) FROM Point pt " +
            "WHERE pt.attendeeId = :attendeeId AND pt.amount < 0")
    Instant getLastResetDate(@Param("attendeeId") Long attendeeId);
}
