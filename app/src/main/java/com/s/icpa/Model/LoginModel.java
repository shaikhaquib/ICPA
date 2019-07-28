package com.s.icpa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("batch_no")
    @Expose
    private String batchNo;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("current_fleet")
    @Expose
    private String currentFleet;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("sap_no")
    @Expose
    private String sapNo;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("work_email")
    @Expose
    private String workEmail;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("verify_status")
    @Expose
    private String verifyStatus;
    @SerializedName("member")
    @Expose
    private String member;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("wedding_date")
    @Expose
    private String weddingDate;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getCustomerId() {
        return customerId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getName() {
        return name;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public void setName(String name) {
        this.name = name;
    }



    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrentFleet() {
        return currentFleet;
    }

    public void setCurrentFleet(String currentFleet) {
        this.currentFleet = currentFleet;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSapNo() {
        return sapNo;
    }

    public void setSapNo(String sapNo) {
        this.sapNo = sapNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}