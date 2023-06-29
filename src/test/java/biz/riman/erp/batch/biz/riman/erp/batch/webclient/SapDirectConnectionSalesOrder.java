package biz.riman.erp.batch.biz.riman.erp.batch.webclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
class SapDirectConnectionSalesOrder {
    private final WebClient webClient;

    private String host = "https://my404942-api.s4hana.cloud.sap";
    private String uri = "/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder";
    private String userName = "Z_SO_TEST_USER";
    private String password = "rjfbBEnqWrjuwzNYWhnwZ}JuRLeyXwV9BcQRRcmF";
    
    final int repeatCount = 1;
    String token = "";
    MultiValueMap<String, String> myCookies = new LinkedMultiValueMap<String, String>();

    @Autowired
    public SapDirectConnectionSalesOrder() {
        // webClient 기본 설정
        log.info("WebClient 기본 설정");
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                 .build();
        this.webClient  = WebClient
            .builder()
            .baseUrl(host)
            .exchangeStrategies(exchangeStrategies)
            .build();
    }
    
    public void getToken() {
        // token 인증
        log.info("token 인증");
        webClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .headers(headers -> {
                    headers.add("x-csrf-token", "fetch");
                    headers.setBasicAuth(userName, password);
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
        
        log.debug("### TOKEN : {}", token);
        log.debug("### myCookies : {}", myCookies);
    }

    @Test
    @DisplayName("SAP direct connection 테스트")
    void test() {
        log.info("## SAP direct connection 시작 ##");
        log.info("");
        
        getToken();
        String _token = new String(token);
        
        for (int i = 0; i < repeatCount; i++) {
            // Param setting
            log.info("## Param setting ##");
//            List<PricingElementModel> pricingElements = new ArrayList<PricingElementModel>();
//            PricingElementModel pricingElement = new PricingElementModel("YBHD", "2500", "KRW");
//            pricingElements.add(pricingElement);
//            
//            List<ItemModel> items = new ArrayList<ItemModel>();
//            ItemModel item = new ItemModel("20", "QM001", "2", "PC");
//            items.add(item);
//            
//            List<AddressModel> addresses = new ArrayList<AddressModel>();
//            AddressModel address = new AddressModel("강남특별시시", "강남남타워 1차", "마포포구테헤란로 509 test");
//            addresses.add(address);
//            
//            List<PartnerModel> partners = new ArrayList<PartnerModel>();
//            PartnerModel partner = new PartnerModel("WE", "0043100001", "1", addresses);
//            partners.add(partner);
//            
//            SalesOrderModel param = new SalesOrderModel("TA", "4310", "00", "10", "0043100001", "TEST20230707", 
//                    "2023-06-21T00:00:00", "2023-06-21T00:00:00", "2023-06-21T00:00:00", 
//                    pricingElements, items, partners);
            Map<String, Object> bodyMap = new HashMap<String, Object>();
            bodyMap.put("SalesOrderType", "TA");
            bodyMap.put("SalesOrganization", "4310");
            bodyMap.put("OrganizationDivision", "00");
            bodyMap.put("DistributionChannel", "10");
            bodyMap.put("SoldToParty", "0043100001");
            bodyMap.put("PurchaseOrderByCustomer", "TEST20230707");
            bodyMap.put("RequestedDeliveryDate", "2023-06-21T00:00:00");
            bodyMap.put("CustomerPurchaseOrderDate", "2023-06-21T00:00:00");
            bodyMap.put("SalesOrderDate", "2023-06-21T00:00:00");
            List<Map<String, String>> to_PricingElement = new ArrayList<Map<String, String>>();
            Map<String, String> pricingElemen = new HashMap<String, String>();
            pricingElemen.put("ConditionType", "YBHD");
            pricingElemen.put("ConditionRateValue", "2500");
            pricingElemen.put("ConditionCurrency", "KRW");
            to_PricingElement.add(pricingElemen);
            bodyMap.put("to_PricingElement", to_PricingElement);
            List<Map<String, String>> to_Item = new ArrayList<Map<String, String>>();
            Map<String, String> item = new HashMap<String, String>();
            item.put("SalesOrderItem", "10");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "1");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "20");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "30");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "40");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "50");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "60");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "70");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "80");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "90");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            item = new HashMap<String, String>();
            item.put("SalesOrderItem", "100");
            item.put("Material", "QM001");
            item.put("RequestedQuantity", "2");
            item.put("RequestedQuantityUnit", "PC");
            to_Item.add(item);
            bodyMap.put("to_Item", to_Item);
            List<Map<String, Object>> to_Partner = new ArrayList<Map<String, Object>>();
            Map<String, Object> partner = new HashMap<String, Object>();
            partner.put("PartnerFunction", "WE");
            partner.put("Customer", "0043100001");
            partner.put("AddressID", "1");
            List<Map<String, String>> to_Address = new ArrayList<Map<String, String>>();
            Map<String, String> address = new HashMap<String, String>();
            address.put("CityName", "강남특별시시");
            address.put("DistrictName", "강남남타워 1차");
            address.put("StreetName", "마포포구테헤란로 509 test");
            to_Address.add(address);
            partner.put("to_Address", to_Address);
            to_Partner.add(partner);
            bodyMap.put("to_Partner", to_Partner);
            
            // api 요청
            log.info("## api 요청 ## {} : {}", _token, i);
            String response = webClient
                .post()
                .uri(uri)
                .accept(MediaType.ALL)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> {
                    headers.add("x-csrf-token", _token);
                    headers.setBasicAuth(userName, password);
                })
                .cookies(cookies -> cookies.addAll(myCookies))
                .body(BodyInserters.fromValue(bodyMap))
//                .retrieve()
//                .bodyToMono(String.class)
//                .onErrorResume(e -> {
//                    if (e instanceof UnknownHostException) {
//                        log.warn("Failed, Host를 확인해주세요.");
//                    } else {
//                        log.error("Failed, API에서 오류가 발생하였습니다. 확인바랍니다.");
//                    }
//                    return Mono.just(new String());
//                })
//                .subscribe(r -> {
//                    log.info("## result : {}", r);
//                });
                .exchangeToMono(res -> {
                    log.info("## http Status {}", res.statusCode());
                    if (res.statusCode().is2xxSuccessful()) {
                        return res.bodyToMono(String.class);
                    } else {
                        return res.createException().flatMap(Mono::error);
                    }
                })
                .block();
            log.info("## 결과 : {}", response);
        }
    }

}
