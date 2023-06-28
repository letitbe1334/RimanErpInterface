package biz.riman.erp.batch.biz.riman.erp.batch.webclient;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import biz.riman.erp.batch.service.SapConnectionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
class SapBridge {
    private static final Logger logger = LoggerFactory.getLogger(SapBridge.class);
    
    @Autowired
    private SapConnectionService service;

    @Test
    void test() {
        String response = service.SapDirectConnectionSalesOrder();
        logger.info("## response : {}", response);
    }

}
