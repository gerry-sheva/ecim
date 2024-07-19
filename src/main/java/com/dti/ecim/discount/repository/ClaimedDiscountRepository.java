package com.dti.ecim.discount.repository;

import com.dti.ecim.discount.dao.AvailableDiscountDao;
import com.dti.ecim.discount.entity.ClaimedDiscount;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClaimedDiscountRepository extends JpaRepository<ClaimedDiscount, Long> {
    Optional<ClaimedDiscount> findByAttendeeIdAndDiscountId(Long attendeeId,  Long discountId);
    Optional<ClaimedDiscount> findByAttendeeIdAndId(Long attendeeId,  Long id);
    @Query(value = """
        SELECT c.id as id, d.name as name, d.description as description, d.amount_flat as amount_flat, d.amount_percent as amount_percent
        FROM claimed_discount AS c
        JOIN discount AS d ON d.id = c.discount_id
        LEFT JOIN redeemed_discount as r ON c.id = r.claimed_discount_id
        WHERE c.attendee_id = :attendeeId and c.expired_at > now() and r.claimed_discount_id is null
          AND (d.event_id = :eventId OR d.type = 'GLOBAL')
    """, nativeQuery = true)
    List<AvailableDiscountDao> retrieveAvailableDiscount(@Param("attendeeId") Long attendeeId, @Param("eventId") Long eventId);
}
