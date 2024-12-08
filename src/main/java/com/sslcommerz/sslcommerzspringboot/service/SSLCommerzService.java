package com.sslcommerz.sslcommerzspringboot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sslcommerz.sslcommerzspringboot.model.RefundResponse;
import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import com.sslcommerz.sslcommerzspringboot.model.ValidationResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SSLCommerzService {

    private final WebClient.Builder webClientBuilder;

    @Value("${sslcommerz.api.sandbox.base-url}")
    private String sandboxBaseUrl;

    @Value("${sslcommerz.api.production.base-url}")
    private String productionBaseUrl;

    @Value("${sslcommerz.api.initiate-transaction-path}")
    private String initiateTransactionPath;

    @Value("${sslcommerz.api.validate-transaction-path}")
    private String validateTransactionPath;

    @Value("${sslcommerz.api.refund-transaction-path}")
    private String refundTransactionPath;

    @Value("${sslcommerz.store-id}")
    private String storeId;

    @Value("${sslcommerz.store-password}")
    private String storePassword;

    @Value("${sslcommerz.transaction.success-url}")
    private String successUrl;

    @Value("${sslcommerz.transaction.fail-url}")
    private String failUrl;

    @Value("${sslcommerz.transaction.cancel-url}")
    private String cancelUrl;

    @Value("${sslcommerz.transaction.currency}")
    private String currency;

    @Value("${sslcommerz.test-mode}")
    private Boolean testMode;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        String baseUrl = testMode ? sandboxBaseUrl : productionBaseUrl;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public TransactionResponse initiateTransaction(TransactionRequest request) {
        request.setStore_id(this.storeId);
        request.setStore_passwd(this.storePassword);
        request.setSuccess_url(this.successUrl);
        request.setFail_url(this.failUrl);
        request.setCancel_url(this.cancelUrl);
        request.setCurrency(this.currency);

        return this.webClient.post()
                .uri(initiateTransactionPath)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("store_id", request.getStore_id())
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

    public Mono<ValidationResponse> validateTransaction(String sessionKey) {
        String url = String.format(
                validateTransactionPath + "?sessionkey=%s&store_id=%s&store_passwd=%s&format=json",
                sessionKey, storeId, storePassword);

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response ->
                        response.bodyToMono(String.class).map(RuntimeException::new))
                .bodyToMono(ValidationResponse.class);
    }

    public Mono<RefundResponse> validateRefund(String bankTranId, String refundAmount, String refundRemarks) {
        String url = String.format(
                refundTransactionPath + "?bank_tran_id=%s&refund_amount=%s&refund_remarks=%s&store_id=%s&store_passwd=%s&v=1&format=json",
                bankTranId, refundAmount, refundRemarks, storeId, storePassword);

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(RefundResponse.class);
    }
}
