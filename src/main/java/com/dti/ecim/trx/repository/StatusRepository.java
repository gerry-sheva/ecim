package com.dti.ecim.trx.repository;

import com.dti.ecim.trx.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
