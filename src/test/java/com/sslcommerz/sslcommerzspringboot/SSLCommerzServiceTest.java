package com.sslcommerz.sslcommerzspringboot;

import com.sslcommerz.sslcommerzspringboot.service.SSLCommerzService;
import org.junit.jupiter.api.Test;
import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;
import com.sslcommerz.sslcommerzspringboot.model.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.sslcommerz.sslcommerzspringboot.model.RefundResponse;
import com.sslcommerz.sslcommerzspringboot.model.ValidationResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SSLCommerzServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private SSLCommerzService sslCommerzService;

    @BeforeEach
    void setUp() {
        // Set up properties using ReflectionTestUtils
        ReflectionTestUtils.setField(sslCommerzService, "storeId", "testStoreId");
        ReflectionTestUtils.setField(sslCommerzService, "storePassword", "testPassword");
        ReflectionTestUtils.setField(sslCommerzService, "successUrl", "http://success.com");
        ReflectionTestUtils.setField(sslCommerzService, "failUrl", "http://fail.com");
        ReflectionTestUtils.setField(sslCommerzService, "cancelUrl", "http://cancel.com");
        ReflectionTestUtils.setField(sslCommerzService, "currency", "BDT");
        ReflectionTestUtils.setField(sslCommerzService, "testMode", true);
        ReflectionTestUtils.setField(sslCommerzService, "sandboxBaseUrl", "https://sandbox.sslcommerz.com");
        ReflectionTestUtils.setField(sslCommerzService, "initiateTransactionPath", "/gwprocess/v4/api.php");
        ReflectionTestUtils.setField(sslCommerzService, "validateTransactionPath", "/validator/api/validationserverAPI.php");
        ReflectionTestUtils.setField(sslCommerzService, "refundTransactionPath", "/validator/api/merchantTransIDvalidationAPI.php");

        // Mock WebClient builder chain
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        sslCommerzService.init();
    }

    @Test
    void initiateTransaction_Success() {
        // Prepare test data
        TransactionRequest request = new TransactionRequest();
        request.setTotalAmount("1000");
        request.setTranId("TEST123");
        request.setCusName("Test Customer");
        request.setCusEmail("test@test.com");

        TransactionResponse expectedResponse = new TransactionResponse();
        expectedResponse.setStatus("SUCCESS");

        // Mock WebClient chain for POST request
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(TransactionResponse.class)).thenReturn(Mono.just(expectedResponse));

        // Execute test
        TransactionResponse response = sslCommerzService.initiateTransaction(request);

        // Verify response
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
    }

    @Test
    void validateTransaction_Success() {
        // Prepare test data
        String tranId = "TEST123";
        ValidationResponse expectedResponse = new ValidationResponse();
        expectedResponse.setStatus("VALID");
        expectedResponse.setTran_id(tranId);

        // Mock WebClient chain for GET request
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ValidationResponse.class)).thenReturn(Mono.just(expectedResponse));

        // Execute and verify test using StepVerifier
        Mono<ValidationResponse> responseMono = sslCommerzService.validateTransaction(tranId);

        StepVerifier.create(responseMono)
                .expectNextMatches(response ->
                        response.getStatus().equals("VALID") &&
                                response.getTran_id().equals(tranId))
                .verifyComplete();
    }

    @Test
    void validateRefund_Success() {
        // Prepare test data
        String bankTranId = "BANK123";
        String refundAmount = "500";
        String refundRemarks = "Test refund";

        RefundResponse expectedResponse = new RefundResponse();
        expectedResponse.setStatus("SUCCESS");
        expectedResponse.setBank_tran_id(bankTranId);

        // Mock WebClient chain for GET request
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(RefundResponse.class)).thenReturn(Mono.just(expectedResponse));

        // Execute and verify test using StepVerifier
        Mono<RefundResponse> responseMono = sslCommerzService.validateRefund(bankTranId, refundAmount, refundRemarks);

        StepVerifier.create(responseMono)
                .expectNextMatches(response ->
                        response.getStatus().equals("SUCCESS") &&
                                response.getBank_tran_id().equals(bankTranId))
                .verifyComplete();
    }
}