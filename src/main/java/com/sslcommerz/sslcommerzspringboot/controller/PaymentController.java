package com.sslcommerz.sslcommerzspringboot.controller;

import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import com.sslcommerz.sslcommerzspringboot.model.ValidationResponse;
import com.sslcommerz.sslcommerzspringboot.service.SSLCommerzService;
import com.sslcommerz.sslcommerzspringboot.util.ParameterBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/ipn-listener")
    public ResponseEntity<String> handleIPN(@RequestParam Map<String, String> ipnData) {
        // Log the incoming data
        System.out.println("IPN Data Received: " + ipnData);

        // Extract required fields
        String tranId = ipnData.get("tran_id");
        String valId = ipnData.get("val_id");
        String amount = ipnData.get("amount");
        String status = ipnData.get("status");
        String verifySign = ipnData.get("verify_sign");

        // Verify the data (e.g., validate the signature, check transaction status)
        if ("VALID".equals(status)) {
            // Process the successful transaction
            System.out.println("Transaction ID: " + tranId + " is valid with amount: " + amount);

            // Perform additional operations such as updating your database or notifying the user
            return ResponseEntity.ok("IPN received and processed successfully");
        } else {
            // Handle invalid or failed transactions
            System.out.println("Transaction ID: " + tranId + " is invalid or failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IPN processing failed");
        }
    }

    @GetMapping("/validate")
    public Mono<ValidationResponse> validateTransaction(
            @RequestParam String sessionKey,
            @RequestParam String storeId,
            @RequestParam String storePassword) {
        return sslCommerzService.validateTransaction(sessionKey, storeId, storePassword);
    }
}
