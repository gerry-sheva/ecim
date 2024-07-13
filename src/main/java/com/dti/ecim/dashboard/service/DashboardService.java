package com.dti.ecim.dashboard.service;

import com.dti.ecim.event.dto.RetrieveEventResponseDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.enums.TimeSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

public interface DashboardService {
    void getStatistics(Instant date, TimeSpecifier timeSpecifier);
    Page<RetrieveEventResponseDto> displayOrganizerEvents(Pageable pageable);
    Page<TrxResponseDto> summarizeTrxs(Pageable pageable, Instant date, TimeSpecifier timeSpecifier);
}
