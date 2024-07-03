package com.dti.ecim.trx.service;

import com.dti.ecim.trx.entity.Trx;

public interface TrxService {
    Trx retrieveTrx(Long trxId);
    Trx createTrx(Trx trx);
    Trx updateTrx(Trx trx);
    void deleteTrx(Trx trx);
}
