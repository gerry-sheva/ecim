package com.dti.ecim.discount.service.impl;

import com.dti.ecim.auth.dto.UserIdResponseDto;
import com.dti.ecim.auth.service.AuthService;
import com.dti.ecim.discount.dto.*;
import com.dti.ecim.discount.entity.*;
import com.dti.ecim.discount.exceptions.InsufficientPointException;
import com.dti.ecim.discount.exceptions.InvalidDiscountException;
import com.dti.ecim.discount.repository.*;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final RedeemedDiscountRepository redeemedDiscountRepository;
    private final ClaimedDiscountRepository claimedDiscountRepository;
    private final PointRepository pointRepository;
    private final PointRedisRepository pointRedisRepository;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Override
    public void createGlobalDiscount(CreateGlobalDiscountRequestDto requestDto) {
        createDiscount(modelMapper.map(requestDto, GlobalDiscount.class));
    }

    @Override
    public Discount createEventDiscount(CreateEventDiscountRequestDto requestDto) {
        return createDiscount(modelMapper.map(requestDto, EventDiscount.class));
    }

    private Discount createDiscount(Discount discount) {
        discount.setExpiredAt(Instant.now().plus(discount.getExpiresInDays(), ChronoUnit.DAYS));
        discount.setCode(discount.getCode().toUpperCase());
        return discountRepository.save(discount);
    }

    @Override
    public void claimDiscount(ClaimDiscountRequestDto requestDto) {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Optional<Discount> discountOptional = discountRepository.findByCode(requestDto.getCode());
        if (discountOptional.isEmpty()) {
            throw new DataNotFoundException(String.format("Discount with code '%s' not found", requestDto.getCode()));
        }

        Discount discount = discountOptional.get();
        if (discount.getExpiredAt().isBefore(Instant.now())) {
            throw new InvalidDiscountException(String.format("Discount with code '%s' is already expired", requestDto.getCode()));
        }

        Optional<ClaimedDiscount> existingDiscount = claimedDiscountRepository.findByAttendeeIdAndDiscountId(userIdResponseDto.getId(), discount.getId());
        if (existingDiscount.isPresent()) {
            throw new InvalidDiscountException("Discount with id: " + requestDto.getCode() + " already claimed");
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
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Optional<ClaimedDiscount> claimedDiscountOptional = claimedDiscountRepository.findByAttendeeIdAndDiscountId(userIdResponseDto.getId(), requestDto.getRedeemedDiscountId());
//        If discount is not found, throw DataNotFoundException
        if (claimedDiscountOptional.isEmpty()) {
            throw new DataNotFoundException("Discount with id: " + requestDto.getRedeemedDiscountId() + " not found");
        }

//        If discount is already expired, invalidate it
        ClaimedDiscount claimedDiscount = claimedDiscountOptional.get();
        if (claimedDiscount.getExpiredAt().isBefore(Instant.now())) {
            throw new InvalidDiscountException(String.format("Discount with id '%s' is already expired", claimedDiscount.getDiscountId()));
        }

//        If discount is an event discount and the event id is not match, invalidate it
        if (claimedDiscount.getDiscount().getType().equals("EVENT")) {
            var eventDiscountDao = discountRepository.retrieveEventDiscount(claimedDiscount.getDiscountId());
            if (!Objects.equals(eventDiscountDao.getEventId(), requestDto.getEventId())) {
                throw new InvalidDiscountException("Discount is invalid");
            }
        }

//        If discount is already redeemed, invalidate it
        Optional<RedeemedDiscount> redeemedDiscountOptional = redeemedDiscountRepository.findById(claimedDiscount.getId());
        if (redeemedDiscountOptional.isPresent()) {
            throw new InvalidDiscountException("Discount with id: " + requestDto.getRedeemedDiscountId() + " already redeemed");
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
            RedeemDiscountResponseDto redeemDiscountResponseDto = redeemDiscount(new RedeemDiscountRequestDto(requestDto.getDiscountId(), requestDto.getEventId()));
            if (redeemDiscountResponseDto.getAmountFlat() > 0) {
                processDiscountResponseDto.addDiscountValue(redeemDiscountResponseDto.getAmountFlat());
            } else {
                int discountValue = requestDto.getTotalPrice() * redeemDiscountResponseDto.getAmountPercent() / 100;
                processDiscountResponseDto.addDiscountValue(discountValue);
            }
        }

        if (requestDto.isUsingPoint()) {
            int points = retrievePoints();
            if (points < 0) {
                throw new InsufficientPointException("Insufficient points");
            }
            Point deductedPoint = new Point();
            deductedPoint.setAmount(points * -1);
            deductedPoint.setAttendeeId(userIdResponseDto.getId());
            deductedPoint.setExpiredAt(Instant.now());
            pointRepository.save(deductedPoint);
            pointRedisRepository.delete(userIdResponseDto.getEmail());
        }
        if (requestDto.getTotalPrice() < processDiscountResponseDto.getDiscountValue()) {
            processDiscountResponseDto.setDiscountValue(requestDto.getTotalPrice());
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

    @Override
    public int retrievePoints() {
        UserIdResponseDto userIdResponseDto = authService.getCurrentUserId();
        Optional<Integer> pointOptional = pointRedisRepository.getPoints(userIdResponseDto.getEmail());
        if (pointOptional.isPresent()) {
            return pointOptional.get();
        } else {
            Instant lastUsed = pointRepository.getLastResetDate(userIdResponseDto.getId());
            if (lastUsed != null && lastUsed.isAfter(Instant.now().minus(90, ChronoUnit.DAYS))) {
                return pointRepository.getCurrentPoints(userIdResponseDto.getId(), Instant.now(), lastUsed);
            } else {
                return pointRepository.getCurrentPoints(userIdResponseDto.getId(), Instant.now(), Instant.now().minus(90, ChronoUnit.DAYS));
            }
        }
    }
}
