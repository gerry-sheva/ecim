package com.dti.ecim.discount.service.impl;

import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.discount.dto.*;
import com.dti.ecim.discount.entity.*;
import com.dti.ecim.discount.repository.RedeemedDiscountRepository;
import com.dti.ecim.discount.repository.DiscountRepository;
import com.dti.ecim.discount.repository.PointRepository;
import com.dti.ecim.discount.repository.ClaimedDiscountRepository;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final RedeemedDiscountRepository redeemedDiscountRepository;
    private final ClaimedDiscountRepository claimedDiscountRepository;
    private final PointRepository pointRepository;
    private final AuthService authService;
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
    public void claimDiscount(ClaimDiscountRequestDto requestDto) {
        Optional<Discount> discountOptional = discountRepository.findByCode(requestDto.getCode());
        if (discountOptional.isEmpty()) {
            throw new DataNotFoundException(String.format("Discount with code '%s' not found", requestDto.getCode()));
        }
        Discount discount = discountOptional.get();
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Optional<ClaimedDiscount> existingDiscount = claimedDiscountRepository.findByAttendeeIdAndDiscountId(userIdResponseDto.getId(), discount.getId());
        if (existingDiscount.isPresent()) {
//            TODO: THROW DISCOUNT ALREADY CLAIMED EXCEPTION
        }
        ClaimedDiscount claimedDiscount = new ClaimedDiscount();
        claimedDiscount.setDiscountId(discount.getId());
        claimedDiscount.setAttendeeId(userIdResponseDto.getId());
        Instant expiredAt = Instant.now().plus(discount.getExpiresInDays(), ChronoUnit.DAYS);
        if (discount.getExpiredAt().isBefore(expiredAt)) {
            claimedDiscount.setExpiredAt(discount.getExpiredAt());
        } else {
            claimedDiscount.setExpiredAt(expiredAt);
        }
        claimedDiscountRepository.save(claimedDiscount);
    }

    @Override
    public RedeemDiscountResponseDto redeemDiscount(RedeemDiscountRequestDto requestDto) {
        Optional<ClaimedDiscount> redeemedDiscountOptional = claimedDiscountRepository.findById(requestDto.getRedeemedDiscountId());
        if (redeemedDiscountOptional.isEmpty()) {
            throw new DataNotFoundException("Discount with id: " + requestDto.getRedeemedDiscountId() + "not found");
        }
        ClaimedDiscount claimedDiscount = redeemedDiscountOptional.get();
        Optional<RedeemedDiscount> claimedDiscountOptional = redeemedDiscountRepository.findById(claimedDiscount.getDiscountId());
        if (claimedDiscountOptional.isPresent()) {
//            TODO: THROW DISCOUNT ALREADY REDEEMED EXCEPTION
        }
        RedeemedDiscount redeemedDiscount = new RedeemedDiscount();
        redeemedDiscount.setClaimedDiscount(claimedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
        return new RedeemDiscountResponseDto(claimedDiscount.getDiscount().getAmountFlat(), claimedDiscount.getDiscount().getAmountPercent());
    }

    @Override
    public ProcessDiscountResponseDto processDiscount(ProcessDiscountRequestDto requestDto) {
        RedeemDiscountResponseDto redeemDiscountResponseDto = redeemDiscount(new RedeemDiscountRequestDto(requestDto.getDiscountId()));
        ProcessDiscountResponseDto processDiscountResponseDto = new ProcessDiscountResponseDto();
        if (redeemDiscountResponseDto.getAmountFlat() > 0) {
            processDiscountResponseDto.setFinalPrice(requestDto.getTotalPrice() - redeemDiscountResponseDto.getAmountFlat());
            processDiscountResponseDto.setDiscountValue(redeemDiscountResponseDto.getAmountFlat());
        } else {
            int discountValue = requestDto.getTotalPrice() * redeemDiscountResponseDto.getAmountPercent() / 100;
            processDiscountResponseDto.setFinalPrice(requestDto.getTotalPrice() - discountValue);
            processDiscountResponseDto.setDiscountValue(discountValue);
        }
        return processDiscountResponseDto;
    }
}
