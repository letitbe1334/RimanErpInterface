package biz.riman.erp.batch.biz.riman.erp.batch.webclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
class BTPConnectionSalesOrder {
    private static final Logger logger = LoggerFactory.getLogger(BTPConnectionSalesOrder.class);

    private final WebClient webClient;

    final String url = "https://int-test-01.it-cpi006-rt.cfapps.jp10.hana.ondemand.com";

    @Autowired
    public BTPConnectionSalesOrder() {
        logger.info("## Web client 셋팅 ##");
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("riman")
                .authorizationUri("https://int-test-01.it-cpi006-rt.cfapps.jp10.hana.ondemand.com")
                .tokenUri("https://int-test-01.authentication.jp10.hana.ondemand.com/oauth/token")
                .clientId("sb-6d1fa376-3646-474a-83af-bf18d16af027!b4317|it-rt-int-test-01!b134")
                .clientSecret("7cc88252-d8a9-4e13-adfb-f6a24aa6630a$p-s5KH1-NF8hDzG0dMZI7MMSxWaNbZe9NfcHucC40c0=")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .build();
        ReactiveClientRegistrationRepository clientRegistrations = new InMemoryReactiveClientRegistrationRepository(registration);
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("riman");
        oauth.setDefaultOAuth2AuthorizedClient(true);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                 .build();
        
        this.webClient =  WebClient.builder()
                .baseUrl(url)
                .filter(oauth)
//              .exchangeStrategies(exchangeStrategies)
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(configurer -> 
                                        configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder())
                                )
                                .build()
                )
                .build();
    }

    @Test
    void test() {
        logger.info("## BTP Integration Connect _ Authentication Method : OAuth 시작 ##");
        logger.info("");
        
        // Param setting
        logger.info("## Param setting ##");
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
        logger.info("## api 요청 ##");
        String response = this.webClient
                .post()
                .uri("/http/ZSB_SALES_ORDER_01_START_STD")
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("riman"))
                .headers(headers -> {
                    headers.add("User-Agent", "Other");
                })
                .body(BodyInserters.fromValue(bodyMap))
                .exchangeToMono(res -> {
                    return res.bodyToMono(String.class);
//                  if (res.statusCode().is2xxSuccessful()) {
//                      return res.bodyToMono(Map.class);
//                  } else {
//                      return res.createException().flatMap(Mono::error);
//                  }
                })
                .block();
        logger.info("## 결과 : {}", response);
    }

}
