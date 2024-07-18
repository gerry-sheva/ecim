package com.dti.ecim.unit.discount;

import com.dti.ecim.discount.dto.CreateEventDiscountRequestDto;
import com.dti.ecim.discount.dto.CreatedDiscountResponseDto;
import com.dti.ecim.discount.entity.Discount;
import com.dti.ecim.discount.service.DiscountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

@SpringBootTest
public class DiscountServiceTest {
    @Autowired
    private DiscountService discountService;

    @Test
    @WithUserDetails("ORGxnA@mail.com")
    public void test_create_event_discount_with_valid_data() {
        CreateEventDiscountRequestDto requestDto = new CreateEventDiscountRequestDto();
        requestDto.setEventId(1L);
        requestDto.setCode("wham");
        requestDto.setName("Testing Discount for 1L");
        requestDto.setExpiresInDays(90);
        requestDto.setAmountPercent(20);
        requestDto.setAmountFlat(0);
        requestDto.setExpiredAt("07:00:00 PM, Thu 01/1/2026");
        requestDto.setDescription("Testing Discount description");

        CreatedDiscountResponseDto discount = discountService.createEventDiscount(requestDto);

        Assertions.assertThat(discount).isNotNull();
        Assertions.assertThat(discount.getCode()).isNotNull();
        Assertions.assertThat(discount.getCode()).isEqualTo("WHAM");
    }
}
