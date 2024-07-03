package com.dti.ecim.trx.service.impl;

import com.dti.ecim.trx.repository.TrxRepository;
import com.dti.ecim.trx.repository.StatusRepository;
import com.dti.ecim.trx.service.TrxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log
public class TrxServiceImpl implements TrxService {
    private final TrxRepository trxRepository;
    private final StatusRepository statusRepository;
}
