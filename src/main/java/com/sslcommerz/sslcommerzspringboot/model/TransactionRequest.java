package com.sslcommerz.sslcommerzspringboot.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class TransactionRequest {
    // Configuration parameters
    private String storeId;
    private String storePassword;
    private String currency;
    private String successUrl;
    private String failUrl;
    private String cancelUrl;
    // Mandatory parameters
    @NotNull(message="Please provide a valid Amount")
    @NotEmpty(message="Please provide a valid Amount")
    @Value("150.00")
    private String totalAmount;
    @NotNull(message="Please provide a valid transaction ID")
    @NotEmpty(message="Please provide a valid transaction ID")
    @Value("SSLCZ_TEST_01")
    private String tranId;
    @NotNull(message="Please provide a valid Product Category")
    @NotEmpty(message="Please provide a valid Product Category")
    @Value("Test")
    private String productCategory;
    @NotNull(message="Please provide a valid Customer Name")
    @NotEmpty(message="Please provide a valid Customer Name")
    @Value("Test Customer")
    private String cusName;
    @NotNull(message="Please provide a valid Customer Email")
    @NotEmpty(message="Please provide a valid Customer Email")
    @Value("test@test.com")
    private String cusEmail;
    @NotNull(message="Please provide a valid Customer Address")
    @NotEmpty(message="Please provide a valid Customer Address")
    @Value("Test Address")
    private String cusAdd1;
    private String cusAdd2;
    @NotNull(message="Please provide a valid Customer City")
    @NotEmpty(message="Please provide a valid Customer City")
    @Value("Dhaka")
    private String cusCity;
    private String cusState;
    @NotNull(message="Please provide a valid Customer Postcode")
    @NotEmpty(message="Please provide a valid Customer Postcode")
    @Value("1000")
    private String cusPostcode;
    @NotNull(message="Please provide a valid Customer Country")
    @NotEmpty(message="Please provide a valid Customer Country")
    @Value("Bangladesh")
    private String cusCountry;
    @NotNull(message="Please provide a valid Customer Phone")
    @NotEmpty(message="Please provide a valid Customer Phone")
    @Value("0111111111")
    private String cusPhone;
    private String cusFax;
    private String shipName;
    private String shipAdd1;
    private String shipAdd2;
    private String shipCity;
    private String shipState;
    @NotNull(message="Please provide a valid Shipping Postcode")
    @NotEmpty(message="Please provide a valid Shipping Postcode")
    private String shipPostcode;
    private String shipCountry;
    private String multiCardName;
    @NotNull(message="Please provide a valid Shipping Method")
    @NotEmpty(message="Please provide a valid Shipping Method")
    @Value("NO")
    private String shippingMethod;
    @NotNull(message="Please provide a valid Product Name")
    @NotEmpty(message="Please provide a valid Product Name")
    @Value("Test Product")
    private String productName;
    @NotNull(message="Please provide a valid Product Profile")
    @NotEmpty(message="Please provide a valid Product Profile")
    @Value("General")
    private String productProfile;
    private String valueA;
    private String valueB;
    private String valueC;
    private String valueD;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusAdd1() {
        return cusAdd1;
    }

    public void setCusAdd1(String cusAdd1) {
        this.cusAdd1 = cusAdd1;
    }

    public String getCusAdd2() {
        return cusAdd2;
    }

    public void setCusAdd2(String cusAdd2) {
        this.cusAdd2 = cusAdd2;
    }

    public String getCusCity() {
        return cusCity;
    }

    public void setCusCity(String cusCity) {
        this.cusCity = cusCity;
    }

    public String getCusState() {
        return cusState;
    }

    public void setCusState(String cusState) {
        this.cusState = cusState;
    }

    public String getCusPostcode() {
        return cusPostcode;
    }

    public void setCusPostcode(String cusPostcode) {
        this.cusPostcode = cusPostcode;
    }

    public String getCusCountry() {
        return cusCountry;
    }

    public void setCusCountry(String cusCountry) {
        this.cusCountry = cusCountry;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getCusFax() {
        return cusFax;
    }

    public void setCusFax(String cusFax) {
        this.cusFax = cusFax;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAdd1() {
        return shipAdd1;
    }

    public void setShipAdd1(String shipAdd1) {
        this.shipAdd1 = shipAdd1;
    }

    public String getShipAdd2() {
        return shipAdd2;
    }

    public void setShipAdd2(String shipAdd2) {
        this.shipAdd2 = shipAdd2;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipPostcode() {
        return shipPostcode;
    }

    public void setShipPostcode(String shipPostcode) {
        this.shipPostcode = shipPostcode;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getMultiCardName() {
        return multiCardName;
    }

    public void setMultiCardName(String multiCardName) {
        this.multiCardName = multiCardName;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductProfile() {
        return productProfile;
    }

    public void setProductProfile(String productProfile) {
        this.productProfile = productProfile;
    }

    public String getValueA() {
        return valueA;
    }

    public void setValueA(String valueA) {
        this.valueA = valueA;
    }

    public String getValueB() {
        return valueB;
    }

    public void setValueB(String valueB) {
        this.valueB = valueB;
    }

    public String getValueC() {
        return valueC;
    }

    public void setValueC(String valueC) {
        this.valueC = valueC;
    }

    public String getValueD() {
        return valueD;
    }

    public void setValueD(String valueD) {
        this.valueD = valueD;
    }
}