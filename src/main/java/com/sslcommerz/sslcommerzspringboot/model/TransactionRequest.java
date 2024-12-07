package com.sslcommerz.sslcommerzspringboot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private String totalAmount;
    private String transactionId;
    private String successUrl;
    private String failUrl;
    private String cancelUrl;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerCity;
    private String customerPostcode;
    private String customerCountry;
    private String customerPhone;
    private String shippingMethod;
    private String productName;
    private String productCategory;
    private String productProfile;
}

