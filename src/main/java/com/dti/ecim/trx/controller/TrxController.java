package com.dti.ecim.trx.controller;

import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.entity.Trx;
import com.dti.ecim.trx.service.TrxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/trx")
public class TrxController {
    private final TrxService trxService;

    @PostMapping
    public TrxResponseDto createTrx(@RequestBody CreateTrxRequestDto createTrxRequestDto) throws NoSuchAlgorithmException {
        log.info("Creating new trx");
        TrxResponseDto trx = trxService.createTrx(createTrxRequestDto);
        return trx;
    }
}
