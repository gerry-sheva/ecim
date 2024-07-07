package com.dti.ecim.unit.trx;

import com.dti.ecim.trx.dto.CreateTixDto;
import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.service.TrxService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

@SpringBootTest
public class TrxServiceTest {
    @Autowired
    private TrxService trxService;

    CreateTrxRequestDto requestDto = new CreateTrxRequestDto();

    @BeforeEach
    public void setUp() {
        requestDto.setEventId(2L);
        requestDto.setDiscountId(3L);

        CreateTixDto tixDto = new CreateTixDto();
        tixDto.setQuantity(1);
        tixDto.setOfferingId(2L);

        requestDto.setTixes(Set.of(tixDto));
    }

    @Test
    public void test_create_trx_with_valid_data() throws NoSuchAlgorithmException {
        TrxResponseDto responseDto = trxService.createTrx(requestDto);

        Assertions.assertThat(responseDto).isNotNull();
    }
}
