package com.dti.ecim.dashboard.service.impl;

import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.dashboard.dto.RetrieveStatisticsDto;
import com.dti.ecim.dashboard.service.DashboardService;
import com.dti.ecim.event.dto.RetrieveEventResponseDto;
import com.dti.ecim.event.entity.Event;
import com.dti.ecim.event.repository.EventRepository;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.enums.TimeSpecifier;
import com.dti.ecim.trx.repository.TrxRepository;
import com.dti.ecim.trx.repository.TrxSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log
public class DashboardServiceImpl implements DashboardService {
    private final TrxRepository trxRepository;
    private final EventRepository eventRepository;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Override
    public List<RetrieveStatisticsDto> getStatistics(Instant date, TimeSpecifier timeSpecifier) {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        var result = trxRepository.getStatistics(userIdResponseDto.getId(), timeSpecifier.name());
        List<RetrieveStatisticsDto> res = new ArrayList<>();
        result.forEach(r -> {
            RetrieveStatisticsDto dto = modelMapper.map(r, RetrieveStatisticsDto.class);
            res.add(dto);
        });
//        result.forEach(r -> System.out.println(r.getRevenue()));
//        System.out.println(result.getRevenue());
//        System.out.println(result.getCount());
        return res;
    }

    @Override
    public Page<RetrieveEventResponseDto> displayOrganizerEvents(Pageable pageable) {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Page<Event> events = eventRepository.findAllByOrganizerIdOrderByEndingDate(userIdResponseDto.getId(), pageable);
        return events.map(event -> modelMapper.map(event, RetrieveEventResponseDto.class));
    }

    @Override
    public Page<TrxResponseDto> summarizeTrxs(Pageable pageable, Instant date, TimeSpecifier timeSpecifier) {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        var spec = Specification.where(TrxSpecifications.byTime(date, timeSpecifier))
                .and(TrxSpecifications.byOrganizerId(userIdResponseDto.getId()));
        var result = trxRepository.findAll(spec, pageable);
        return result.map(trx -> modelMapper.map(trx, TrxResponseDto.class));
    }
}
