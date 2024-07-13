package com.dti.ecim.trx.service;

import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.enums.TimeSpecifier;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public interface TrxService {
    TrxResponseDto retrieveTrx(Long trxId) throws BadRequestException, NoSuchAlgorithmException;
    TrxResponseDto createTrx(CreateTrxRequestDto createTrxRequestDto) throws NoSuchAlgorithmException, BadRequestException;
    Page<TrxResponseDto> retrieveAllTrx(Pageable pageable);
}
