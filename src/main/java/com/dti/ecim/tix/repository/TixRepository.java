package com.dti.ecim.tix.repository;

import com.dti.ecim.tix.entity.Tix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TixRepository extends JpaRepository<Tix, Long> {
}
