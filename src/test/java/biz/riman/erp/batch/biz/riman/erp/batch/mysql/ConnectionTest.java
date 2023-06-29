package biz.riman.erp.batch.biz.riman.erp.batch.mysql;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import biz.riman.erp.batch.dto.TempTableDto;
import biz.riman.erp.batch.service.DbConnectionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
class ConnectionTest {
    @Autowired
    private DbConnectionService service;

    @Test
    void test() {
        List<TempTableDto> datas = service.getTempTables();
        if (!CollectionUtils.isEmpty(datas)) {
            log.info("## data size : {}", datas.size());
        } else {
            log.warn("## data empty");
        }
    }

}
