package com.sslcommerz.sslcommerzspringboot.controller;

import com.sslcommerz.sslcommerzspringboot.model.RefundResponse;
import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import com.sslcommerz.sslcommerzspringboot.model.ValidationResponse;
import com.sslcommerz.sslcommerzspringboot.service.SSLCommerzService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final SSLCommerzService sslCommerzService;

    @Operation(summary = "Initiate a transaction")
    @PostMapping("/initiate")
    public ResponseEntity<TransactionResponse> initiateTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse response = sslCommerzService.initiateTransaction(transactionRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Handle IPN notifications")
    @PostMapping("/ipn-listener")
    public ResponseEntity<String> handleIPN(@RequestParam Map<String, String> ipnData) {
        // Log the incoming data
        log.info("IPN Data Received: " + ipnData);

        // Extract required fields
        String tranId = ipnData.get("tran_id");
        String valId = ipnData.get("val_id");
        String amount = ipnData.get("amount");
        String status = ipnData.get("status");
        String verifySign = ipnData.get("verify_sign");

        // Verify the data (e.g., validate the signature, check transaction status)
        if ("VALID".equals(status)) {
            // Process the successful transaction
            log.info("Transaction ID: " + tranId + " is valid with amount: " + amount);

            // Perform additional operations such as updating your database or notifying the user
            return ResponseEntity.ok("IPN received and processed successfully");
        } else {
            // Handle invalid or failed transactions
            log.info("Transaction ID: " + tranId + " is invalid or failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IPN processing failed");
        }
    }

    @Operation(summary = "Validate a transaction")
    @GetMapping("/validate")
    public Mono<ValidationResponse> validateTransaction(
            @RequestParam String tran_id) {
        return sslCommerzService.validateTransaction(tran_id);
    }

    @Operation(summary = "Initiate a refund")
    @GetMapping("/initiate-refund")
    public Mono<RefundResponse> initiateRefund(
            @RequestParam String bankTranId,
            @RequestParam String refundAmount,
            @RequestParam String refundRemarks) {
        return sslCommerzService.validateRefund(bankTranId, refundAmount, refundRemarks);
    }
}
