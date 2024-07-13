package com.dti.ecim.trx.repository;

import com.dti.ecim.trx.entity.Trx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxRepository extends JpaRepository<Trx, Long> {
    Page<Trx> findAllByOrganizerId(Long organizerId, Pageable pageable);
    Page<Trx> findAllByAttendeeId(Long attendeeId, Pageable pageable);
}
