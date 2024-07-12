package com.dti.ecim.trx.service;

import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.entity.Trx;
import org.apache.coyote.BadRequestException;

import java.security.NoSuchAlgorithmException;

public interface TrxService {
    Trx retrieveTrx(Long trxId) throws BadRequestException, NoSuchAlgorithmException;
    TrxResponseDto createTrx(CreateTrxRequestDto createTrxRequestDto) throws NoSuchAlgorithmException, BadRequestException;
}
