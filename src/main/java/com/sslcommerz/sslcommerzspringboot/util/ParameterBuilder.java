package com.sslcommerz.sslcommerzspringboot.util;

import com.sslcommerz.sslcommerzspringboot.model.TransactionRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ParameterBuilder {

    public static Map<String, String> fromTransactionRequest(TransactionRequest request) {
        Map<String, String> postData = new HashMap<>();
        postData.put("total_amount", request.getTotalAmount());
        postData.put("tran_id", request.getTransactionId());
        postData.put("success_url", request.getSuccessUrl());
        postData.put("fail_url", request.getFailUrl());
        postData.put("cancel_url", request.getCancelUrl());
        postData.put("cus_name", request.getCustomerName());
        postData.put("cus_email", request.getCustomerEmail());
        postData.put("cus_add1", request.getCustomerAddress());
        postData.put("cus_city", request.getCustomerCity());
        postData.put("cus_postcode", request.getCustomerPostcode());
        postData.put("cus_country", request.getCustomerCountry());
        postData.put("cus_phone", request.getCustomerPhone());
        postData.put("shipping_method", request.getShippingMethod());
        postData.put("product_name", request.getProductName());
        postData.put("product_category", request.getProductCategory());
        postData.put("product_profile", request.getProductProfile());
        return postData;
    }

}
