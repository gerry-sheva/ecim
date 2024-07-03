package com.dti.ecim.trx.repository;

import com.dti.ecim.trx.entity.Trx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxRepository extends JpaRepository<Trx, Long> {
}
