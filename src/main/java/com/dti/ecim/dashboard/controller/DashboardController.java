package com.dti.ecim.dashboard.controller;

import com.dti.ecim.dashboard.service.DashboardService;
import com.dti.ecim.trx.enums.TimeSpecifier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public void getStatistics(
            @RequestParam(defaultValue = "DAY") String timeSpecifier,
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant date
    ) {
        TimeSpecifier timeSpecifierEnum = TimeSpecifier.valueOf(timeSpecifier.toUpperCase());
        dashboardService.getStatistics(Instant.now(), timeSpecifierEnum);
    }

    @GetMapping("/events")
    public ResponseEntity<?> displayOrganizersEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        var res = dashboardService.displayOrganizerEvents(PageRequest.of(page, size));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/trxs")
    public ResponseEntity<?> sumTrxs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "DAY") String timeSpecifier,
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant date
    ) {
        TimeSpecifier timeSpecifierEnum = TimeSpecifier.valueOf(timeSpecifier.toUpperCase());
        var res = dashboardService.summarizeTrxs(PageRequest.of(page, size), date, timeSpecifierEnum);
        return ResponseEntity.ok(res);
    }
}