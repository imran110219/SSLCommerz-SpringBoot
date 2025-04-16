package com.sslcommerz.sslcommerzspringboot.service;

import com.sslcommerz.sslcommerzspringboot.model.RefundResponse;
import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import com.sslcommerz.sslcommerzspringboot.model.ValidationResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
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

    public SSLCommerzService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostConstruct
    public void init() {
        String baseUrl = testMode ? sandboxBaseUrl : productionBaseUrl;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public TransactionResponse initiateTransaction(TransactionRequest request) {
        request.setStoreId(this.storeId);
        request.setStorePassword(this.storePassword);
        request.setSuccessUrl(this.successUrl);
        request.setFailUrl(this.failUrl);
        request.setCancelUrl(this.cancelUrl);
        request.setCurrency(this.currency);

        return this.webClient.post()
                .uri(initiateTransactionPath)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("store_id", request.getStoreId())
                        .with("store_passwd", request.getStorePassword())
                        .with("total_amount", request.getTotalAmount())
                        .with("currency", request.getCurrency())
                        .with("tran_id", request.getTranId())
                        .with("success_url", request.getSuccessUrl())
                        .with("fail_url", request.getFailUrl())
                        .with("cancel_url", request.getCancelUrl())
                        .with("cus_name", request.getCusName())
                        .with("cus_email", request.getCusEmail())
                        .with("cus_add1", request.getCusAdd1())
                        .with("cus_add2", request.getCusAdd2())
                        .with("cus_city", request.getCusCity())
                        .with("cus_state", request.getCusState())
                        .with("cus_postcode", request.getCusPostcode())
                        .with("cus_country", request.getCusCountry())
                        .with("cus_phone", request.getCusPhone())
                        .with("cus_fax", request.getCusFax())
                        .with("ship_name", request.getShipName())
                        .with("ship_add1", request.getShipAdd1())
                        .with("ship_add2", request.getShipAdd2())
                        .with("ship_city", request.getShipCity())
                        .with("ship_state", request.getShipState())
                        .with("ship_postcode", request.getShipPostcode())
                        .with("ship_country", request.getShipCountry())
                        .with("multi_card_name", request.getMultiCardName())
                        .with("shipping_method", request.getShippingMethod())
                        .with("product_name", request.getProductName())
                        .with("product_category", request.getProductCategory())
                        .with("product_profile", request.getProductProfile())
                        .with("value_a", request.getValueA())
                        .with("value_b", request.getValueB())
                        .with("value_c", request.getValueC())
                        .with("value_d", request.getValueD()))
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public Mono<ValidationResponse> validateTransaction(String tran_id) {
        String url = String.format(
                validateTransactionPath + "?val_id=%s&store_id=%s&store_passwd=%s&format=json",
                tran_id, storeId, storePassword);

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
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
