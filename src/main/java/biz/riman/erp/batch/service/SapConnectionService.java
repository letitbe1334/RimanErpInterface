package biz.riman.erp.batch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import biz.riman.erp.batch.dto.salesOrder.AddressDto;
import biz.riman.erp.batch.dto.salesOrder.ItemDto;
import biz.riman.erp.batch.dto.salesOrder.PartnerDto;
import biz.riman.erp.batch.dto.salesOrder.PricingElementDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderDto;
import biz.riman.erp.batch.dto.salesOrder.SalesOrderSapConnectionDto;
import reactor.core.publisher.Mono;

@Service
public class SapConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(SapConnectionService.class);

    @Autowired
    private SalesOrderSapConnectionDto salesOrderSapConnectionDto;

    private final WebClient localApiClient;

    String token = "";
    MultiValueMap<String, String> myCookies = new LinkedMultiValueMap<String, String>();
    
    @Autowired
    public SapConnectionService(@Qualifier("sapSalesOrder") WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    
    public String SapDirectConnectionSalesOrder() {
        String response = new String("");
        logger.info("## SAP direct connection 시작 ##");
        logger.info("");
        
        getToken();
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
        logger.info("## api 요청 ## {}", token);
        response = localApiClient
            .post()
            .uri(salesOrderSapConnectionDto.getUri())
            .accept(MediaType.ALL)
            .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .contentType(MediaType.APPLICATION_JSON)
            .headers(headers -> {
                headers.add("x-csrf-token", token);
                headers.setBasicAuth(salesOrderSapConnectionDto.getUserName(), salesOrderSapConnectionDto.getPassword());
            })
            .cookies(cookies -> cookies.addAll(myCookies))
            .body(BodyInserters.fromValue(param))
//            .retrieve()
//            .bodyToMono(String.class)
//            .onErrorResume(e -> {
//                if (e instanceof UnknownHostException) {
//                    logger.warn("Failed, Host를 확인해주세요.");
//                } else {
//                    logger.error("Failed, API에서 오류가 발생하였습니다. 확인바랍니다.");
//                }
//                return Mono.just(new String());
//            })
//            .subscribe(r -> {
//                logger.info("## result : {}", r);
//            });
            .exchangeToMono(res -> {
                logger.info("## http Status {}", res.statusCode());
                if (res.statusCode().is2xxSuccessful()) {
                    return res.bodyToMono(String.class);
                } else {
                    return res.createException().flatMap(Mono::error);
                }
            })
            .block();
        
        return response;
    }

    public void getToken() {
        // token 인증
        logger.info("token 인증");
        localApiClient
                .get()
                .uri(salesOrderSapConnectionDto.getUri())
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .headers(headers -> {
                    headers.add("x-csrf-token", "fetch");
                    headers.setBasicAuth(salesOrderSapConnectionDto.getUserName(), salesOrderSapConnectionDto.getPassword());
                })
                .exchangeToMono(res -> {
                    if (res.statusCode().equals(HttpStatus.OK)) {
                      for (String key: res.cookies().keySet()) {
                          myCookies.put(key, Arrays.asList(res.cookies().get(key).get(0).getValue()));
                      }
                      List<String> tokens = res.headers().asHttpHeaders().get("x-csrf-token");
                        if (tokens != null && tokens.size() > 0) {
                            token = tokens.get(0);
                            return res.bodyToMono(Map.class);
                        } else {
                            return res.createException().flatMap(Mono::error);
                        }
                    } else {
                        return res.createException().flatMap(Mono::error);
                    }
                })
                .block();
        
        logger.debug("### TOKEN : {}", token);
        logger.debug("### myCookies : {}", myCookies);
    }
}
