package com.dti.ecim.tix.service.impl;

import com.dti.ecim.tix.entity.Tix;
import com.dti.ecim.tix.repository.TixRepository;
import com.dti.ecim.tix.service.TixService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Log
public class TixServiceImpl implements TixService {
    private final TixRepository tixRepository;

    @Override
    public Tix createTix(Tix tix) {
        return null;
    }
}
