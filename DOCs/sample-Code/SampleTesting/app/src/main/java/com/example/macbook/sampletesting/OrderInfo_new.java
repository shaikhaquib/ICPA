package com.example.macbook.sampletesting;


import java.io.Serializable;

public class OrderInfo_new implements Serializable {
    private static final String responseURL = "https://pg.qpayindia.com/wwws/Payment/response.aspx";
    private static final String requestURL = "https://pg.qpayindia.com/wwws/Payment/PaymentDetails.aspx";
    private static final String transactionType = "PURCHASE";
    private static final String paymentPageRequired = "Y";
    private static final String getPaymentDetails = "https://pg.qpayindia.com/wwws/Payment/response.aspx/getPaymentDetails";
    private String mode;
    private String qpayID;
    private String qpayPWD;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postal_code;
    private String phone;
    private String email;
    private String paymentOption;
    private String currencyCode;
    private String orderID;

    private String secure_hash;

    private String responseActivity;
    public OrderInfo_new() {
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getQpayID() {
        return this.qpayID;
    }

    public void setQpayID(String qpayID) {
        this.qpayID = qpayID;
    }

    public String getQpayPWD() {
        return this.qpayPWD;
    }

    public void setQpayPWD(String qpayPWD) {
        this.qpayPWD = qpayPWD;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal_code() {
        return this.postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentOption() {
        return this.paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public static String getRequestURL() {
        return "https://pg.qpayindia.com/wwws/Payment/PaymentDetails.aspx";
    }

    public static String getResponseURL() {
        return "https://pg.qpayindia.com/wwws/Payment/response.aspx";
    }

    public static String getTransactionType() {
        return "PURCHASE";
    }

    public static String getPaymentPageRequired() {
        return "Y";
    }

    public static String getGetPaymentDetails() {
        return "https://pg.qpayindia.com/wwws/Payment/response.aspx/getPaymentDetails";
    }

    public String getResponseActivity() {
        return this.responseActivity;
    }

    public void setResponseActivity(String responseActivity) {
        this.responseActivity = responseActivity;
    }

    public String getSecure_hash() {
        return secure_hash;
    }

    public void setSecure_hash(String secure_hash) {
        this.secure_hash = secure_hash;
    }
}

