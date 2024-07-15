package com.dti.ecim.unit.trx;

import com.dti.ecim.exceptions.DataNotFoundException;
import com.dti.ecim.trx.dto.CreateTixDto;
import com.dti.ecim.trx.dto.CreateTrxRequestDto;
import com.dti.ecim.trx.dto.TrxResponseDto;
import com.dti.ecim.trx.service.TrxService;
import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.test.context.support.WithUserDetails;

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
        requestDto.setOrganizerId(1L);
        CreateTixDto tixDto = new CreateTixDto();
        tixDto.setQuantity(1);
        tixDto.setOfferingId(2L);

        requestDto.setTixes(Set.of(tixDto));
    }
//    RETRIEVE TRX
    @Test
    @Disabled
    @WithUserDetails("qweas@mail.com")
    public void test_retrieve_trx_with_valid_id() throws BadRequestException, NoSuchAlgorithmException {
        TrxResponseDto responseDto = trxService.retrieveTrx(302L);

        Assertions.assertThat(responseDto).isNotNull();
    }

    @Test
    @WithUserDetails("qweas@mail.com")
    public void test_retrieve_trx_with_invalid_id() throws BadRequestException, NoSuchAlgorithmException {
        Assertions.assertThatThrownBy(() -> trxService.retrieveTrx(-1L)).isInstanceOf(DataNotFoundException.class);
    }

    @Test
    @WithUserDetails("qweas@mail.com")
    public void test_retrieve_trx_with_invalid_ownership() throws BadRequestException, NoSuchAlgorithmException {
        Assertions.assertThatThrownBy(() -> trxService.retrieveTrx(2L)).isInstanceOf(DataNotFoundException.class);
    }


//    CREATE TRX
    @Test
    @WithUserDetails("qweas@mail.com")
    public void test_create_trx_with_valid_data() throws NoSuchAlgorithmException, BadRequestException {
        TrxResponseDto responseDto = trxService.createTrx(requestDto);

        Assertions.assertThat(responseDto).isNotNull();
    }

    @Test
    @WithUserDetails("qweas@mail.com")
    public void test_create_trx_with_invalid_organizer() throws BadRequestException, NoSuchAlgorithmException {
        requestDto.setOrganizerId(-1L);

        Assertions.assertThatThrownBy(() -> trxService.createTrx(requestDto)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @WithUserDetails("qweas@mail.com")
    public void test_create_trx_with_invalid_event() throws BadRequestException, NoSuchAlgorithmException {
        requestDto.setEventId(-1L);

        Assertions.assertThatThrownBy(() -> trxService.createTrx(requestDto)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @WithUserDetails("qweas@mail.com")
    public void test_create_trx_with_invalid_tix() throws BadRequestException, NoSuchAlgorithmException {
        requestDto.setTixes(Set.of(new CreateTixDto()));

        Assertions.assertThatThrownBy(() -> trxService.createTrx(requestDto)).isInstanceOf(InvalidDataAccessApiUsageException.class);
    }
}
