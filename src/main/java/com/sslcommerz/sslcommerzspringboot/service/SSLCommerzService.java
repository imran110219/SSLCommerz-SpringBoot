package com.sslcommerz.sslcommerzspringboot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionResponse.class)
                .block();
    }

    public Mono<Boolean> validateTransactionResponse(String transactionAmount,
                                                     String transactionCurrency,
                                                     Map<String, String> request) {
        if (!request.containsKey("tran_id")) {
            throw new IllegalArgumentException("Transaction ID is required.");
        }

        String transactionId = request.get("tran_id");
        return validateOrder(transactionId, transactionAmount, transactionCurrency, request);
    }

    private Mono<Boolean> validateOrder(String transactionId, String amount, String currency, Map<String, String> requestParams) {
        if (!ipnHashVerify(requestParams)) {
            logger.warn("IPN hash verification failed.");
            return Mono.just(false);
        }

        String url = validationPath + "?val_id=" + encode(requestParams.get("val_id"))
                + "&store_id=" + encode(this.storeId)
                + "&store_passwd=" + encode(this.storePassword)
                + "&v=1&format=json";

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> {
                    logger.info("Validation Response: {}", response);
                    String status = response.path("status").asText();
                    return ("VALID".equals(status) || "VALIDATED".equals(status))
                            && transactionId.equals(response.path("tran_id").asText())
                            && Math.abs(Double.parseDouble(amount) - response.path("currency_amount").asDouble()) < 1
                            && currency.equals(response.path("currency_type").asText());
                })
                .onErrorResume(e -> {
                    logger.error("Error during validation: {}", e.getMessage(), e);
                    return Mono.just(false);
                });
    }

    private boolean ipnHashVerify(Map<String, String> requestParams) {
        String verifySign = requestParams.get("verify_sign");
        String verifyKey = requestParams.get("verify_key");

        if (verifySign == null || verifyKey == null) {
            logger.warn("Missing IPN verification keys.");
            return false;
        }

        String[] keyList = verifyKey.split(",");
        TreeMap<String, String> sortedMap = new TreeMap<>();

        for (String key : keyList) {
            sortedMap.put(key, requestParams.get(key));
        }

        sortedMap.put("store_passwd", md5(this.storePassword));

        StringBuilder hashString = new StringBuilder();
        sortedMap.forEach((key, value) -> hashString.append(key).append("=").append(value).append("&"));

        String generatedHash = md5(hashString.substring(0, hashString.length() - 1));
        boolean verified = generatedHash.equals(verifySign);
        logger.info("IPN hash verification result: {}", verified);
        return verified;
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5 algorithm not available", e);
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    private String parseGatewayUrl(String response) throws Exception {
        JsonNode jsonResponse = objectMapper.readTree(response);
        return jsonResponse.path("GatewayPageURL").asText(null);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}