package com.dti.ecim.trx.service.impl;

import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.discount.dto.ProcessDiscountRequestDto;
import com.dti.ecim.discount.dto.ProcessDiscountResponseDto;
import com.dti.ecim.discount.dto.RedeemDiscountRequestDto;
import com.dti.ecim.discount.dto.RedeemDiscountResponseDto;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.event.dto.EventOfferingResponseDto;
import com.dti.ecim.event.entity.EventOffering;
import com.dti.ecim.event.service.EventService;
import com.dti.ecim.trx.entity.Tix;
import com.dti.ecim.trx.dto.CreateTixDto;
import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.entity.Trx;
import com.dti.ecim.trx.enums.Status;
import com.dti.ecim.trx.helper.TrxHelper;
import com.dti.ecim.trx.repository.TrxRepository;
import com.dti.ecim.trx.service.TrxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Log
public class TrxServiceImpl implements TrxService {
    private final TrxRepository trxRepository;
    private final EventService eventService;
    private final AuthService authService;
    private final DiscountService discountService;
    private final ModelMapper modelMapper;


    @Override
    public Trx retrieveTrx(Long trxId) {
        return null;
    }

    @Override
    @Transactional
    public TrxResponseDto createTrx(CreateTrxRequestDto createTrxRequestDto) throws NoSuchAlgorithmException {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Trx trx = new Trx();
        trx.setEventId(createTrxRequestDto.getEventId());
        int totalPrice = 0;
        trx.setAttendeeId(userIdResponseDto.getId());
        trx.setStatus(Status.WAITING_PAYMENT);
        Set<CreateTixDto> tixDtos = createTrxRequestDto.getTixes();
        for (CreateTixDto tixDto : tixDtos) {
            EventOfferingResponseDto eventOffering = eventService.getEventOffering(tixDto.getOfferingId());
            for (int i = 0; i < tixDto.getQuantity(); i++) {
                Tix tix = new Tix();
                tix.setCode(TrxHelper.generateTixCode(eventOffering.getName()));
                tix.setEventOffering(modelMapper.map(eventOffering, EventOffering.class));
                trx.addTix(tix);
                totalPrice += eventOffering.getPrice();
            }
        }
        trx.setPrice(totalPrice);
        if (createTrxRequestDto.getDiscountId() != null || createTrxRequestDto.isPoint())  {
            ProcessDiscountResponseDto processDiscountResponseDto = discountService.processDiscount(
                    new ProcessDiscountRequestDto(
                            createTrxRequestDto.getDiscountId(),
                            createTrxRequestDto.isPoint(),
                            totalPrice
                    ));
            trx.setDiscountValue(processDiscountResponseDto.getDiscountValue());
            trx.setFinalPrice(processDiscountResponseDto.getFinalPrice());
            trx.setDiscountId(trx.getDiscountId());
        } else {
            trx.setDiscountValue(0);
            trx.setFinalPrice(totalPrice);
        }

        Trx savedTrx = trxRepository.save(trx);
        return modelMapper.map(savedTrx, TrxResponseDto.class);
    }

    @Override
    public Trx updateTrx(Trx trx) {
        return null;
    }

    @Override
    public void deleteTrx(Trx trx) {

    }
}
