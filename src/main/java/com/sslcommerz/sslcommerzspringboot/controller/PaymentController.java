package com.sslcommerz.sslcommerzspringboot.controller;

import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import com.sslcommerz.sslcommerzspringboot.service.SSLCommerzService;
import com.sslcommerz.sslcommerzspringboot.util.ParameterBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final SSLCommerzService sslCommerzService;

    @PostMapping("/initiate")
    public ResponseEntity<TransactionResponse> initiateTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse response = sslCommerzService.initiateTransaction(transactionRequest);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/validate")
//    public Mono<Boolean> validateTransactionResponse(@RequestParam(defaultValue = "150.00") String transactionAmount,
//                                                     @RequestParam(defaultValue = "BDT") String transactionCurrency,
//                                                     @RequestBody TransactionRequest transactionRequest) {
//        Map<String, String> request = ParameterBuilder.fromTransactionRequest(transactionRequest);
//        return sslCommerzService.validateTransactionResponse(transactionAmount, transactionCurrency, request);
//    }
}
