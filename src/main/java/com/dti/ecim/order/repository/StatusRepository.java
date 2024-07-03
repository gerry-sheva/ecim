package com.dti.ecim.order.repository;

import com.dti.ecim.order.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
