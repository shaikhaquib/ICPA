package com.s.icpa.Qpay;

import java.io.Serializable;

/**
 * SDK Base Class.
 */

public class OrderInfo implements Serializable {


    /**
     * Base URL for production environment.
     */

    private static final String responseURL = "https://pg.qpayindia.com/wwws/Payment/response.aspx",
            requestURL = "https://pg.qpayindia.com/wwws/Payment/PaymentDetails.aspx",
            transactionType = "PURCHASE", paymentPageRequired = "Y",
            getPaymentDetails = "https://pg.qpayindia.com/wwws/Payment/response.aspx/getPaymentDetails";

    private String mode;

    private String qpayID;

    private String qpayPWD;

    private String submerchantID;

    private String submerchantName;

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

    private String responseActivity;

    /**
     * @return get Current mode.
     */

    public String getMode() {
        return mode;
    }

    /**
     * @return set Current mode.
     */

    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return get QpayID.
     */

    public String getQpayID() {
        return qpayID;
    }

    /**
     * @return set QpayID.
     */

    public void setQpayID(String qpayID) {
        this.qpayID = qpayID;
    }

    /**
     * @return get QpayPWD.
     */

    public String getQpayPWD() {
        return qpayPWD;
    }

    /**
     * @return set QpayPWD.
     */

    public void setQpayPWD(String qpayPWD) {
        this.qpayPWD = qpayPWD;
    }


    public String getSubmerchantID() {
        return submerchantID;
    }

    public void setSubmerchantID(String submerchantID) {
        this.submerchantID = submerchantID;
    }

    public String getSubmerchantName() {
        return submerchantName;
    }

    public void setSubmerchantName(String submerchantName) {
        this.submerchantName = submerchantName;
    }

    /**
     * @return get name.
     */

    public String getName() {
        return name;
    }

    /**
     * @return set name.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return get address.
     */

    public String getAddress() {
        return address;
    }

    /**
     * @return set address.
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return get city.
     */

    public String getCity() {
        return city;
    }

    /**
     * @return set city.
     */

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return get state.
     */

    public String getState() {
        return state;
    }

    /**
     * @return set state.
     */

    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return get country.
     */

    public String getCountry() {
        return country;
    }

    /**
     * @return set country.
     */

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return get postal_code.
     */

    public String getPostal_code() {
        return postal_code;
    }

    /**
     * @return set postal_code.
     */

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * @return get phone.
     */

    public String getPhone() {
        return phone;
    }

    /**
     * @return set phone.
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return get email.
     */

    public String getEmail() {
        return email;
    }

    /**
     * @return set email.
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return get paymentOption.
     */

    public String getPaymentOption() {
        return paymentOption;
    }

    /**
     * @return get paymentOption.
     */

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    /**
     * @return get currencyCode.
     */

    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @return set currencycode.
     */

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return get orderID.
     */

    public String getOrderID() {
        return orderID;
    }

    /**
     * @return set orderID.
     */

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return get requestURL.
     */

    public static String getRequestURL() {
        return requestURL;
    }

    /**
     * @return get responseURL.
     */

    public static String getResponseURL() {
        return responseURL;
    }

    /**
     * @return get transactionType.
     */

    public static String getTransactionType() {
        return transactionType;
    }

    /**
     * @return get paymentPageRequired.
     */

    public static String getPaymentPageRequired() {
        return paymentPageRequired;
    }

    /**
     * @return get paymentDetails.
     */

    public static String getGetPaymentDetails() {
        return getPaymentDetails;
    }

    /**
     * @return get resonseActivity.
     */

    public String getResponseActivity() {
        return responseActivity;
    }

    /**
     * @return set responseActivity.
     */

    public void setResponseActivity(String responseActivity) {
        this.responseActivity = responseActivity;
    }
}
