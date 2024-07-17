package com.dti.ecim.trx.controller;

import com.dti.ecim.response.Response;
import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.enums.TimeSpecifier;
import com.dti.ecim.trx.service.TrxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/trx")
public class TrxController {
    private final TrxService trxService;

    @PostMapping
    public ResponseEntity<?> createTrx(@Valid @RequestBody CreateTrxRequestDto createTrxRequestDto) throws NoSuchAlgorithmException, BadRequestException {
        var res = trxService.createTrx(createTrxRequestDto);
        return Response.success(HttpStatus.CREATED.value(), "Transactions created successfully", res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrxById(@PathVariable Long id) throws NoSuchAlgorithmException, BadRequestException {
        var res = trxService.retrieveTrx(id);
        return Response.success(HttpStatus.OK.value(), "Transaction retrieved successfully", res);
    }

    @GetMapping
    public ResponseEntity<?> retrieveTrxs(
            @RequestParam(defaultValue = "DAY") String timeSpecifier,
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        TimeSpecifier timeSpecifierEnum = TimeSpecifier.valueOf(timeSpecifier.toUpperCase());
        var res = trxService.retrieveAllTrx(PageRequest.of(page, size), date, timeSpecifierEnum);
        return Response.success(HttpStatus.OK.value(), "Transactions retrieved successfully", res);
    }
}
