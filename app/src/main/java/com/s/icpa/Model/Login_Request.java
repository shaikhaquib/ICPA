package com.s.icpa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Login_Request implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("work_email")
    @Expose
    private String workEmail;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("added_time")
    @Expose
    private String addedTime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
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
    @SerializedName("sap_no")
    @Expose
    private String sapNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("wedding_date")
    @Expose
    private String weddingDate;
    @SerializedName("active_date")
    @Expose
    private Object activeDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("member")
    @Expose
    private String member;
    @SerializedName("new_email")
    @Expose
    private String newEmail;
    @SerializedName("new_region")
    @Expose
    private String newRegion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatchNo() {
        return batchNo;
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

    public String getSapNo() {
        return sapNo;
    }

    public void setSapNo(String sapNo) {
        this.sapNo = sapNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public Object getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Object activeDate) {
        this.activeDate = activeDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewRegion() {
        return newRegion;
    }

    public void setNewRegion(String newRegion) {
        this.newRegion = newRegion;
    }

}