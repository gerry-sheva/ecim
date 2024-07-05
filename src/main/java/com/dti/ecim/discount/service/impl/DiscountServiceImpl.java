package com.dti.ecim.discount.service.impl;

import com.dti.ecim.discount.dto.CreateEventDiscountRequestDto;
import com.dti.ecim.discount.dto.CreateGlobalDiscountRequestDto;
import com.dti.ecim.discount.entity.Discount;
import com.dti.ecim.discount.entity.EventDiscount;
import com.dti.ecim.discount.entity.GlobalDiscount;
import com.dti.ecim.discount.entity.RedeemedDiscount;
import com.dti.ecim.discount.repository.ClaimedDiscountRepository;
import com.dti.ecim.discount.repository.DiscountRepository;
import com.dti.ecim.discount.repository.PointRepository;
import com.dti.ecim.discount.repository.RedeemedDiscountRepository;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.user.service.impl.OrganizerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
@Log
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ClaimedDiscountRepository claimedDiscountRepository;
    private final RedeemedDiscountRepository redeemedDiscountRepository;
    private final PointRepository pointRepository;
    private final OrganizerServiceImpl organizerService;
    private final ModelMapper modelMapper;

    @Override
    public void createGlobalDiscount(CreateGlobalDiscountRequestDto requestDto) {
        createDiscount(modelMapper.map(requestDto, GlobalDiscount.class));
    }

    @Override
    public void createEventDiscount(CreateEventDiscountRequestDto requestDto) {
        createDiscount(modelMapper.map(requestDto, EventDiscount.class));
    }

    private void createDiscount(Discount discount) {
        discount.setExpiredAt(Instant.now().plus(discount.getExpiresInDays(), ChronoUnit.DAYS));
        discount.setCode(discount.getCode().toUpperCase());
        discountRepository.save(discount);
    }

    @Override
    public void redeemDiscount(Discount discount) {

    }

    @Override
    public void claimDiscount(RedeemedDiscount redeemedDiscount) {

    }
}
