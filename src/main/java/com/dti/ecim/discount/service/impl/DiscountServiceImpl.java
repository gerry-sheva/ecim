package com.dti.ecim.discount.service.impl;

import com.dti.ecim.discount.dto.CreateEventDiscountRequestDto;
import com.dti.ecim.discount.dto.CreateGlobalDiscountRequestDto;
import com.dti.ecim.discount.dto.RedeemDiscountRequestDto;
import com.dti.ecim.discount.entity.Discount;
import com.dti.ecim.discount.entity.EventDiscount;
import com.dti.ecim.discount.entity.GlobalDiscount;
import com.dti.ecim.discount.entity.RedeemedDiscount;
import com.dti.ecim.discount.repository.ClaimedDiscountRepository;
import com.dti.ecim.discount.repository.DiscountRepository;
import com.dti.ecim.discount.repository.PointRepository;
import com.dti.ecim.discount.repository.RedeemedDiscountRepository;
import com.dti.ecim.discount.service.DiscountService;
import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.user.dto.UserIdResponseDto;
import com.dti.ecim.user.service.AttendeeService;
import com.dti.ecim.user.service.OrganizerService;
import com.dti.ecim.user.service.UserService;
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
    private final ClaimedDiscountRepository claimedDiscountRepository;
    private final RedeemedDiscountRepository redeemedDiscountRepository;
    private final PointRepository pointRepository;
    private final OrganizerService organizerService;
    private final AttendeeService attendeeService;
    private final UserService userService;
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
    public void redeemDiscount(RedeemDiscountRequestDto requestDto) {
        Optional<Discount> discountOptional = discountRepository.findByCode(requestDto.getCode());
        if (discountOptional.isEmpty()) {
            throw new DataNotFoundException(String.format("Discount with code '%s' not found", requestDto.getCode()));
        }
        Discount discount = discountOptional.get();
        UserIdResponseDto userIdResponseDto = userService.getCurrentUserId();
        Optional<RedeemedDiscount> existingDiscount = redeemedDiscountRepository.findByAttendeeIdAndDiscountId(userIdResponseDto.getId(), discount.getId());
        if (existingDiscount.isPresent()) {
//            TODO: THROW DISCOUNT ALREADY REDEEMED EXCEPTION
        }
        RedeemedDiscount redeemedDiscount = new RedeemedDiscount();
        redeemedDiscount.setDiscountId(discount.getId());
        redeemedDiscount.setAttendeeId(userIdResponseDto.getId());
        Instant expiredAt = Instant.now().plus(discount.getExpiresInDays(), ChronoUnit.DAYS);
        if (discount.getExpiredAt().isBefore(expiredAt)) {
            redeemedDiscount.setExpiredAt(discount.getExpiredAt());
        } else {
            redeemedDiscount.setExpiredAt(expiredAt);
        }
        redeemedDiscountRepository.save(redeemedDiscount);
    }

    @Override
    public void claimDiscount(RedeemedDiscount redeemedDiscount) {

    }
}
