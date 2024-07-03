package com.dti.ecim.trx;

import com.dti.ecim.trx.service.TrxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TrxServiceImplIntegrationTest {
    @Autowired
    private TrxService trxService;

    @Test
    public void createOrder() {
//        Trx order = new Trx();
//        order.setStatus(1L);
    }
}
