package com.sslcommerz.sslcommerzspringboot.model;

import lombok.Data;

//curl --location 'https://sandbox.sslcommerz.com/gwprocess/v4/api.php' \
//        --header 'Content-Type: application/x-www-form-urlencoded' \
//        --data-urlencode 'store_id=ciphe650299504f174' \
//        --data-urlencode 'store_passwd=ciphe650299504f174@ssl' \
//        --data-urlencode 'total_amount=100' \
//        --data-urlencode 'currency=EUR' \
//        --data-urlencode 'tran_id=REF123' \
//        --data-urlencode 'success_url=http://yoursite.com/success.php' \
//        --data-urlencode 'fail_url=http://yoursite.com/fail.php' \
//        --data-urlencode 'cancel_url=http://yoursite.com/cancel.php' \
//        --data-urlencode 'cus_name=Customer Name' \
//        --data-urlencode 'cus_email=cust@yahoo.com' \
//        --data-urlencode 'cus_add1=Dhaka' \
//        --data-urlencode 'cus_add2=Dhaka' \
//        --data-urlencode 'cus_city=Dhaka' \
//        --data-urlencode 'cus_state=Dhaka' \
//        --data-urlencode 'cus_postcode=1000' \
//        --data-urlencode 'cus_country=Bangladesh' \
//        --data-urlencode 'cus_phone=01711111111' \
//        --data-urlencode 'cus_fax=01711111111' \
//        --data-urlencode 'ship_name=Customer Name' \
//        --data-urlencode 'ship_add1%20=Dhaka' \
//        --data-urlencode 'ship_add2=Dhaka' \
//        --data-urlencode 'ship_city=Dhaka' \
//        --data-urlencode 'ship_state=Dhaka' \
//        --data-urlencode 'ship_postcode=1000' \
//        --data-urlencode 'ship_country=Bangladesh' \
//        --data-urlencode 'multi_card_name=mastercard,visacard,amexcard' \
//        --data-urlencode 'value_a=ref001_A' \
//        --data-urlencode 'value_b=ref002_B' \
//        --data-urlencode 'value_c=ref003_C' \
//        --data-urlencode 'value_d=ref004_D' \
//        --data-urlencode 'shipping_method=NO' \
//        --data-urlencode 'product_name=test' \
//        --data-urlencode 'product_category=test' \
//        --data-urlencode 'product_profile=test'

@Data
public class TransactionRequest {
    private String store_id;
    private String store_passwd;
    private String total_amount;
    private String currency;
    private String tran_id;
    private String success_url;
    private String fail_url;
    private String cancel_url;
    private String cus_name;
    private String cus_email;
    private String cus_add1;
    private String cus_add2;
    private String cus_city;
    private String cus_state;
    private String cus_postcode;
    private String cus_country;
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
    private String product_category;
    private String product_profile;
    private String value_a;
    private String value_b;
    private String value_c;
    private String value_d;
}