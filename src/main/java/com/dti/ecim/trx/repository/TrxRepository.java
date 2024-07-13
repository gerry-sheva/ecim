package com.dti.ecim.trx.repository;

import com.dti.ecim.trx.entity.Trx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.time.LocalDate;

public interface TrxRepository extends JpaRepository<Trx, Long>, JpaSpecificationExecutor<Trx> {
    Page<Trx> findAllByOrganizerId(Long organizerId, Pageable pageable);
    Page<Trx> findAllByAttendeeId(Long attendeeId, Pageable pageable);
    Page<Trx> findAllByOrganizerId(Long organizerId, Specification<Trx> spec, Pageable pageable);

    @Query(value = "SELECT * FROM trx WHERE DATE(created_at) = ?1", nativeQuery = true)
    Page<Trx> findAllByDate(LocalDate createdAt, Pageable pageable);

    @Query(value = "SELECT * FROM trx WHERE EXTRACT(MONTH FROM created_at) = ?1 AND EXTRACT(YEAR FROM created_at) = ?2;", nativeQuery = true)
    Page<Trx> findAllByMonth(int month, int year, Pageable pageable);

    @Query(value = "SELECT * FROM trx WHERE EXTRACT(YEAR FROM created_at) = 2024;", nativeQuery = true)
    Page<Trx> findAllByYear(Pageable pageable);


}
