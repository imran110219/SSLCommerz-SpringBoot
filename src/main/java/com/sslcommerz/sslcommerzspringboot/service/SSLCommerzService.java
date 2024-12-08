package com.sslcommerz.sslcommerzspringboot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import com.sslcommerz.sslcommerzspringboot.model.ValidationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SSLCommerzService {

    private static final Logger logger = LoggerFactory.getLogger(SSLCommerzService.class);
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String initiateTransactionPath;
    private final String validationPath;
    private final String storeId;
    private final String storePassword;
    private final String successUrl;
    private final String failUrl;
    private final String cancelUrl;
    private final String currency;

    public SSLCommerzService(
            @Value("${sslcommerz.api.base-url}") String baseUrl,
            @Value("${sslcommerz.api.initiate-transaction-path}") String initiateTransactionPath,
            @Value("${sslcommerz.api.validation-path}") String validationPath,
            @Value("${sslcommerz.store-id}") String storeId,
            @Value("${sslcommerz.store-password}") String storePassword,
            @Value("${sslcommerz.transaction.success-url}") String successUrl,
            @Value("${sslcommerz.transaction.fail-url}") String failUrl,
            @Value("${sslcommerz.transaction.cancel-url}") String cancelUrl,
            @Value("${sslcommerz.transaction.currency}") String currency,
            WebClient.Builder webClientBuilder,
            ObjectMapper objectMapper
    ) {
        this.baseUrl = baseUrl;
        this.initiateTransactionPath = initiateTransactionPath;
        this.validationPath = validationPath;
        this.storeId = storeId;
        this.storePassword = storePassword;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
        this.cancelUrl = cancelUrl;
        this.currency = currency;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.objectMapper = objectMapper;
    }

    public TransactionResponse initiateTransaction(TransactionRequest request) {

        request.setStore_id(this.storeId);
        request.setStore_passwd(this.storePassword);
        request.setSuccess_url(this.successUrl);
        request.setFail_url(this.failUrl);
        request.setCancel_url(this.cancelUrl);
        request.setCurrency(this.currency);

        return this.webClient.post()
                .uri(baseUrl+initiateTransactionPath)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData(
                        "store_id", request.getStore_id())
                        .with("store_passwd", request.getStore_passwd())
                        .with("total_amount", request.getTotal_amount())
                        .with("currency", request.getCurrency())
                        .with("tran_id", request.getTran_id())
                        .with("success_url", request.getSuccess_url())
                        .with("fail_url", request.getFail_url())
                        .with("cancel_url", request.getCancel_url())
                        .with("cus_name", request.getCus_name())
                        .with("cus_email", request.getCus_email())
                        .with("cus_add1", request.getCus_add1())
                        .with("cus_add2", request.getCus_add2())
                        .with("cus_city", request.getCus_city())
                        .with("cus_state", request.getCus_state())
                        .with("cus_postcode", request.getCus_postcode())
                        .with("cus_country", request.getCus_country())
                        .with("cus_phone", request.getCus_phone())
                        .with("cus_fax", request.getCus_fax())
                        .with("ship_name", request.getShip_name())
                        .with("ship_add1", request.getShip_add1())
                        .with("ship_add2", request.getShip_add2())
                        .with("ship_city", request.getShip_city())
                        .with("ship_state", request.getShip_state())
                        .with("ship_postcode", request.getShip_postcode())
                        .with("ship_country", request.getShip_country())
                        .with("multi_card_name", request.getMulti_card_name())
                        .with("shipping_method", request.getShipping_method())
                        .with("product_name", request.getProduct_name())
                        .with("product_category", request.getProduct_category())
                        .with("product_profile", request.getProduct_profile())
                        .with("value_a", request.getValue_a())
                        .with("value_b", request.getValue_b())
                        .with("value_c", request.getValue_c())
                        .with("value_d", request.getValue_d()))
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public Mono<ValidationResponse> validateTransaction(String sessionKey, String storeId, String storePassword) {
        String url = String.format(
                "/validator/api/merchantTransIDvalidationAPI.php?sessionkey=%s&store_id=%s&store_passwd=%s&format=json",
                sessionKey, storeId, storePassword);

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response ->
                        response.bodyToMono(String.class).map(RuntimeException::new))
                .bodyToMono(ValidationResponse.class);
    }
}
