package com.dti.ecim.discount.service.impl;

import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.discount.dto.*;
import com.dti.ecim.discount.entity.*;
import com.dti.ecim.discount.exceptions.InsufficientPointException;
import com.dti.ecim.discount.repository.RedeemedDiscountRepository;
import com.dti.ecim.discount.repository.DiscountRepository;
import com.dti.ecim.discount.repository.PointRepository;
import com.dti.ecim.discount.repository.ClaimedDiscountRepository;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
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

    private RedeemDiscountResponseDto redeemDiscount(RedeemDiscountRequestDto requestDto) {
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
    public ProcessDiscountResponseDto processDiscount(ProcessDiscountRequestDto requestDto) throws BadRequestException {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        ProcessDiscountResponseDto processDiscountResponseDto = new ProcessDiscountResponseDto();
        if (requestDto.getDiscountId() != null) {
            RedeemDiscountResponseDto redeemDiscountResponseDto = redeemDiscount(new RedeemDiscountRequestDto(requestDto.getDiscountId()));
            if (redeemDiscountResponseDto.getAmountFlat() > 0) {
                processDiscountResponseDto.addDiscountValue(redeemDiscountResponseDto.getAmountFlat());
            } else {
                int discountValue = requestDto.getTotalPrice() * redeemDiscountResponseDto.getAmountPercent() / 100;
                processDiscountResponseDto.addDiscountValue(discountValue);
            }
        }
        if (requestDto.isUsingPoint()) {
            Instant lastUsed = pointRepository.getLastResetDate(userIdResponseDto.getId());
            int points;
            if (lastUsed.isAfter(Instant.now().minus(90, ChronoUnit.DAYS))) {
                points = pointRepository.getCurrentPoints(userIdResponseDto.getId(), Instant.now(), lastUsed);
                processDiscountResponseDto.addDiscountValue(points);
            } else {
                points = pointRepository.getCurrentPoints(userIdResponseDto.getId(), Instant.now(), Instant.now().minus(90, ChronoUnit.DAYS));
                processDiscountResponseDto.addDiscountValue(points);
            }
            if (points < 0) {
                throw new BadRequestException("Insufficient points");
            }
            Point deductedPoint = new Point();
            deductedPoint.setAmount(points * -1);
            deductedPoint.setAttendeeId(userIdResponseDto.getId());
            deductedPoint.setExpiredAt(Instant.now());
            pointRepository.save(deductedPoint);

        }
        processDiscountResponseDto.setFinalPrice(requestDto.getTotalPrice() - processDiscountResponseDto.getDiscountValue());
        return processDiscountResponseDto;
    }

    @Override
    public void addPoint(Long referralId) {
        Point point = new Point();
        point.setAttendeeId(referralId);
        point.setAmount(10000);
        point.setExpiredAt(Instant.now().plus(90, ChronoUnit.DAYS));
        pointRepository.save(point);
    }
}
