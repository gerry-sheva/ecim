package com.dti.ecim.trx.service.impl;

import com.dti.ecim.auth.entity.UserAuth;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.trx.entity.Status;
import com.dti.ecim.trx.entity.Trx;
import com.dti.ecim.trx.repository.TrxRepository;
import com.dti.ecim.trx.repository.StatusRepository;
import com.dti.ecim.trx.service.TrxService;
import com.dti.ecim.user.entity.Attendee;
import com.dti.ecim.user.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class TrxServiceImpl implements TrxService {
    private final TrxRepository trxRepository;
    private final StatusRepository statusRepository;
    private final AttendeeService attendeeService;

    @Override
    public Trx retrieveTrx(Long trxId) {
        return null;
    }

    @Override
    public Trx createTrx() {
        var ctx = SecurityContextHolder.getContext();
        Authentication authentication = ctx.getAuthentication();
        Attendee attendee = attendeeService.findAttendeeByEmail(authentication.getName());
        Optional<Status> waiting = statusRepository.findById(1L);
        if (waiting.isEmpty()) {
            throw new DataNotFoundException("Data is not found");
        }
        Trx trx = new Trx();
        log.info(attendee.getFname());
        trx.setAttendee(attendee);

        trx.setStatus(waiting.get());
        return trxRepository.save(trx);
    }

    @Override
    public Trx updateTrx(Trx trx) {
        return null;
    }

    @Override
    public void deleteTrx(Trx trx) {

    }
}
