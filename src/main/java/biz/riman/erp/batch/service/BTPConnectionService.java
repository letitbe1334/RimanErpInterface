package biz.riman.erp.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import biz.riman.erp.batch.dto.salesOrder.AddressDto;
import biz.riman.erp.batch.dto.salesOrder.ItemDto;
import biz.riman.erp.batch.dto.salesOrder.PartnerDto;
import biz.riman.erp.batch.dto.salesOrder.PricingElementDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderDto;

@Service
public class BTPConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(BTPConnectionService.class);

    private final WebClient localApiClient;
    
    @Autowired
    public BTPConnectionService(@Qualifier("oAuth2SalesOrder") WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    public String BTPConnectionSalesOrder() {
        String response = new String("");
        logger.info("## BTP Integration Connect _ Authentication Method : OAuth 시작 ##");
        logger.info("");
        
        logger.info("## Param setting ##");
        List<PricingElementDto> pricingElements = new ArrayList<PricingElementDto>();
        PricingElementDto pricingElement = new PricingElementDto("YBHD", "2500", "KRW");
        pricingElements.add(pricingElement);
        
        List<ItemDto> items = new ArrayList<ItemDto>();
        ItemDto item = new ItemDto("20", "QM001", "2", "PC");
        items.add(item);
        
        List<AddressDto> addresses = new ArrayList<AddressDto>();
        AddressDto address = new AddressDto("강남특별시시", "강남남타워 1차", "마포포구테헤란로 509 test");
        addresses.add(address);
        
        List<PartnerDto> partners = new ArrayList<PartnerDto>();
        PartnerDto partner = new PartnerDto("WE", "0043100001", "1", addresses);
        partners.add(partner);
        
        SalesOrderDto param = new SalesOrderDto("TA", "4310", "00", "10", "0043100001", "TEST20230707", 
                "2023-06-21T00:00:00", "2023-06-21T00:00:00", "2023-06-21T00:00:00", 
                pricingElements, items, partners);
        
        // api 요청
        logger.info("## api 요청 ##");
        response = localApiClient
                .post()
                .uri("/http/ZSB_SALES_ORDER_01_START_STD")
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("riman"))
                .headers(headers -> {
                    headers.add("User-Agent", "Other");
                })
                .body(BodyInserters.fromValue(param))
                .exchangeToMono(res -> {
                    return res.bodyToMono(String.class);
//                  if (res.statusCode().is2xxSuccessful()) {
//                      return res.bodyToMono(Map.class);
//                  } else {
//                      return res.createException().flatMap(Mono::error);
//                  }
                })
                .block();
        
        return response;
    }
}
