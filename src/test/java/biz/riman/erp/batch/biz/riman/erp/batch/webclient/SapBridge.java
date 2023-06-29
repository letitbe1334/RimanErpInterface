package biz.riman.erp.batch.biz.riman.erp.batch.webclient;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import biz.riman.erp.batch.service.SapConnectionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
class SapBridge {
    @Autowired
    private SapConnectionService service;

    @Test
    void test() {
        String response = service.SapDirectConnectionSalesOrder();
        log.info("## response : {}", response);
    }

}
