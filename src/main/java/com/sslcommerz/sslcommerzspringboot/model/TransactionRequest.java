package com.sslcommerz.sslcommerzspringboot.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequest {
    // Configuration parameters
    private String store_id;
    private String store_passwd;
    private String currency;
    private String success_url;
    private String fail_url;
    private String cancel_url;
    // Mandatory parameters
    @NotNull(message="Please provide a valid Amount")
    @NotEmpty(message="Please provide a valid Amount")
    private String total_amount;
    @NotNull(message="Please provide a valid transaction ID")
    @NotEmpty(message="Please provide a valid transaction ID")
    private String tran_id;
    @NotNull(message="Please provide a valid Product Category")
    @NotEmpty(message="Please provide a valid Product Category")
    private String product_category;
    @NotNull(message="Please provide a valid Customer Name")
    @NotEmpty(message="Please provide a valid Customer Name")
    private String cus_name;
    @NotNull(message="Please provide a valid Customer Email")
    @NotEmpty(message="Please provide a valid Customer Email")
    private String cus_email;
    @NotNull(message="Please provide a valid Customer Address")
    @NotEmpty(message="Please provide a valid Customer Address")
    private String cus_add1;
    private String cus_add2;
    @NotNull(message="Please provide a valid Customer City")
    @NotEmpty(message="Please provide a valid Customer City")
    private String cus_city;
    private String cus_state;
    @NotNull(message="Please provide a valid Customer Postcode")
    @NotEmpty(message="Please provide a valid Customer Postcode")
    private String cus_postcode;
    @NotNull(message="Please provide a valid Customer Country")
    @NotEmpty(message="Please provide a valid Customer Country")
    private String cus_country;
    @NotNull(message="Please provide a valid Customer Phone")
    @NotEmpty(message="Please provide a valid Customer Phone")
    private String cus_phone;
    private String cus_fax;
    private String ship_name;
    private String ship_add1;
    private String ship_add2;
    private String ship_city;
    private String ship_state;
    private String ship_postcode;
    private String ship_country;
    private String multi_card_name;
    private String shipping_method;
    private String product_name;
    private String product_profile;
    private String value_a;
    private String value_b;
    private String value_c;
    private String value_d;
}