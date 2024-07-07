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
        requestDto.setEventId(1L);
        requestDto.setDiscountId(1L);

        CreateTixDto tixDto = new CreateTixDto();
        tixDto.setQuantity(2);
        tixDto.setOfferingId(1L);

        CreateTixDto tixDto2 = new CreateTixDto();
        tixDto2.setQuantity(1);
        tixDto2.setOfferingId(2L);

        requestDto.setTixes(Set.of(tixDto, tixDto2));
    }

    @Test
    public void test_create_trx_with_valid_data() throws NoSuchAlgorithmException {
        TrxResponseDto responseDto = trxService.createTrx(requestDto);

        Assertions.assertThat(responseDto).isNotNull();
    }
}
