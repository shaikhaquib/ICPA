package com.s.icpa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ACM_Model implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("flight_type")
    @Expose
    private String flightType;
    @SerializedName("flight_no")
    @Expose
    private String flightNo;
    @SerializedName("travel_date")
    @Expose
    private String travelDate;
    @SerializedName("staff_no")
    @Expose
    private String staffNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("leave_from")
    @Expose
    private String leaveFrom;
    @SerializedName("leave_to")
    @Expose
    private String leaveTo;
    @SerializedName("current_fleet")
    @Expose
    private String currentFleet;
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

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLeaveFrom() {
        return leaveFrom;
    }

    public void setLeaveFrom(String leaveFrom) {
        this.leaveFrom = leaveFrom;
    }

    public String getLeaveTo() {
        return leaveTo;
    }

    public void setLeaveTo(String leaveTo) {
        this.leaveTo = leaveTo;
    }

    public String getCurrentFleet() {
        return currentFleet;
    }

    public void setCurrentFleet(String currentFleet) {
        this.currentFleet = currentFleet;
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