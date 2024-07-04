package com.dti.ecim.trx.service;

import com.dti.ecim.trx.entity.Trx;
import org.apache.coyote.BadRequestException;

import java.security.NoSuchAlgorithmException;

public interface TrxService {
    Trx retrieveTrx(Long trxId) throws BadRequestException, NoSuchAlgorithmException;
    Trx createTrx();
    Trx updateTrx(Trx trx);
    void deleteTrx(Trx trx);
}
