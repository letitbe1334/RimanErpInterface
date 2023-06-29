package biz.riman.erp.batch.biz.riman.erp.batch.webclient;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import biz.riman.erp.batch.service.BTPConnectionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
class BTPBridge {
    @Autowired
    private BTPConnectionService service;

    @Test
    void test() {
        String response = service.BTPConnectionSalesOrder(LocalDate.now());
        log.info("## response : {}", response);
    }

}
