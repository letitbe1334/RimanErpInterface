package biz.riman.erp.batch.config.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import biz.riman.erp.batch.dto.salesOrder.SalesOrderSapConnectionDto;

@Configuration
public class SapClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(SapClientConfig.class);

    @Autowired
    private SalesOrderSapConnectionDto salesOrderSapConnectionDto;

    @Bean(name = "sapSalesOrder")
    public WebClient sapSalesOrder() {
        logger.info("## SapSalesOrder WebClient 세팅 ##");
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                 .build();
        
        return WebClient.builder()
                .baseUrl(salesOrderSapConnectionDto.getEndpointHost())
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
