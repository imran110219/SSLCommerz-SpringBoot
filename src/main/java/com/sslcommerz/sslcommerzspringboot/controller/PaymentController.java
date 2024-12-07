package com.sslcommerz.sslcommerzspringboot.controller;

import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.service.SSLCommerzClient;
import com.sslcommerz.sslcommerzspringboot.util.ParameterBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final SSLCommerzClient sslCommerzClient;

    @PostMapping("/initiate")
    public Mono<String> initiateTransaction(@RequestBody TransactionRequest transactionRequest) {
        Map<String, String> postData = ParameterBuilder.fromTransactionRequest(transactionRequest);
        return sslCommerzClient.initiateTransaction(postData);
    }

    @PostMapping("/validate")
    public Mono<Boolean> validateTransactionResponse(@RequestParam String transactionAmount,
                                                     @RequestParam String transactionCurrency,
                                                     @RequestBody TransactionRequest transactionRequest) {
        Map<String, String> request = ParameterBuilder.fromTransactionRequest(transactionRequest);
        return sslCommerzClient.validateTransactionResponse(transactionAmount, transactionCurrency, request);
    }
}
