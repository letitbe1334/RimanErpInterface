package biz.riman.erp.batch.config.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import biz.riman.erp.batch.dto.salesOrder.SalesOrderSapConnectionDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SapClientConfig {
    @Autowired
    private SalesOrderSapConnectionDto salesOrderSapConnectionDto;

    @Bean(name = "sapSalesOrder")
    public WebClient sapSalesOrder() {
        log.info("## SapSalesOrder WebClient 세팅 ##");
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                 .build();
        
        return WebClient.builder()
                .baseUrl(salesOrderSapConnectionDto.getEndpointHost())
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
