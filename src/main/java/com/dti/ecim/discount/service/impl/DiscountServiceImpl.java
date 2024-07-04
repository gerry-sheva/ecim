package com.dti.ecim.discount.service.impl;

import com.dti.ecim.discount.repository.ClaimedDiscountRepository;
import com.dti.ecim.discount.repository.DiscountRepository;
import com.dti.ecim.discount.repository.PointRepository;
import com.dti.ecim.discount.repository.RedeemedDiscountRepository;
import com.dti.ecim.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ClaimedDiscountRepository claimedDiscountRepository;
    private final RedeemedDiscountRepository redemedDiscountRepository;
    private final PointRepository pointRepository;
}
