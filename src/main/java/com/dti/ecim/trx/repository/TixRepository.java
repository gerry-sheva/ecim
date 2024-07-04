package com.dti.ecim.trx.repository;

import com.dti.ecim.trx.entity.Tix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TixRepository extends JpaRepository<Tix, Long> {
}
