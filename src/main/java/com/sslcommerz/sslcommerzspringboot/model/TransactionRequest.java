package com.sslcommerz.sslcommerzspringboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
//    @JsonProperty(defaultValue = "150.00")
    private String totalAmount;
//    @JsonProperty(defaultValue = "TESTJAVA1234")
    private String transactionId;
//    @JsonProperty("http://localhost:9000/success-page")
    private String successUrl;
//    @JsonProperty("http://localhost:9000/fail-page")
    private String failUrl;
//    @JsonProperty("http://localhost:9000/cancel-page")
    private String cancelUrl;
//    @JsonProperty("ABC XY")
    private String customerName;
//    @JsonProperty("abc.xyz@example.com")
    private String customerEmail;
//    @JsonProperty("Address Line One")
    private String customerAddress;
//    @JsonProperty("Dhaka")
    private String customerCity;
//    @JsonProperty("1000")
    private String customerPostcode;
//    @JsonProperty("Bangladesh")
    private String customerCountry;
//    @JsonProperty("0111111111")
    private String customerPhone;
//    @JsonProperty("NO")
    private String shippingMethod;
//    @JsonProperty("Test Product")
    private String productName;
//    @JsonProperty("General")
    private String productCategory;
//    @JsonProperty("General")
    private String productProfile;
}

