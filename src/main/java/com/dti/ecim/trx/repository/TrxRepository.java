package com.dti.ecim.trx.repository;

import com.dti.ecim.dashboard.dao.StatisticDao;
import com.dti.ecim.trx.entity.Trx;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrxRepository extends JpaRepository<Trx, Long>, JpaSpecificationExecutor<Trx> {
    Optional<Trx> findByIdAndAttendeeId(Long id, Long attendeeId);
    Optional<Trx> findByIdAndOrganizerId(Long id, Long organizerId);
    Page<Trx> findAllByOrganizerId(Long organizerId, Pageable pageable);
    Page<Trx> findAllByAttendeeId(Long attendeeId, Pageable pageable);

    @Query(value = "SELECT count(tix.event_offering_id) as count, tix.event_offering_id as offeringId, e.price as price, count(tix.event_offering_id) * e.price as revenue from trx " +
            "join tix on tix.trx_id = trx.id " +
            "join event_offering as e on tix.event_offering_id = e.id " +
            "WHERE date_trunc(:timeSpecifier, trx.created_at) = date_trunc(:timeSpecifier, now()) " +
            "AND trx.organizer_id = :organizerId " +
            "group by tix.event_offering_id, e.price",
            nativeQuery = true
    )
    List<StatisticDao> getStatistics(@Param("organizerId") Long organizerId, @Param("timeSpecifier") String timeSpecifier);
}
