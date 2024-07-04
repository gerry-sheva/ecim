package com.dti.ecim.trx.controller;

import com.dti.ecim.trx.entity.Trx;
import com.dti.ecim.trx.service.TrxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/trx")
public class TrxController {
    private final TrxService trxService;

    @PostMapping
    public Trx createTrx() {
        log.info("Creating new trx");
        Trx trx =trxService.createTrx();
        return trx;
    }
}
