package com.s.icpa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PMU_Model implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("sap_no")
    @Expose
    private String sapNo;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("document_url")
    @Expose
    private String documentUrl;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("batch_no")
    @Expose
    private String batchNo;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("current_fleet")
    @Expose
    private String currentFleet;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("wedding_date")
    @Expose
    private String weddingDate;
    @SerializedName("active_date")
    @Expose
    private String activeDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("member")
    @Expose
    private String member;
    @SerializedName("new_email")
    @Expose
    private String newEmail;
    @SerializedName("new_region")
    @Expose
    private String newRegion;
    @SerializedName("reason")
    @Expose
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCurrentFleet() {
        return currentFleet;
    }

    public void setCurrentFleet(String currentFleet) {
        this.currentFleet = currentFleet;
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

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}