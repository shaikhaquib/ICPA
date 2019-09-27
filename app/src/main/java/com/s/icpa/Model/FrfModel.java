package com.s.icpa.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FrfModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("employee_no")
    @Expose
    private String employeeNo;
    @SerializedName("local_report_date")
    @Expose
    private String localReportDate;
    @SerializedName("local_report_time")
    @Expose
    private String localReportTime;
    @SerializedName("duty_desc")
    @Expose
    private String dutyDesc;
    @SerializedName("fof")
    @Expose
    private String fof;
    @SerializedName("fot")
    @Expose
    private String fot;
    @SerializedName("hrt")
    @Expose
    private String hrt;
    @SerializedName("disrupt")
    @Expose
    private String disrupt;
    @SerializedName("aircraft_type")
    @Expose
    private String aircraftType;
    @SerializedName("aircraft_no")
    @Expose
    private String aircraftNo;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fpd")
    @Expose
    private String fpd;
    @SerializedName("hotel")
    @Expose
    private String hotel;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("awake")
    @Expose
    private String awake;
    @SerializedName("sleep")
    @Expose
    private String sleep;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("personal")
    @Expose
    private String personal;
    @SerializedName("what_did_you_do")
    @Expose
    private String whatDidYouDo;
    @SerializedName("what_could_be_done")
    @Expose
    private String whatCouldBeDone;
    @SerializedName("other_comment")
    @Expose
    private String otherComment;
    @SerializedName("flightdecknap")
    @Expose
    private String flightdecknap;
    @SerializedName("how_you_felt")
    @Expose
    private String howYouFelt;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getLocalReportDate() {
        return localReportDate;
    }

    public void setLocalReportDate(String localReportDate) {
        this.localReportDate = localReportDate;
    }

    public String getLocalReportTime() {
        return localReportTime;
    }

    public void setLocalReportTime(String localReportTime) {
        this.localReportTime = localReportTime;
    }

    public String getDutyDesc() {
        return dutyDesc;
    }

    public void setDutyDesc(String dutyDesc) {
        this.dutyDesc = dutyDesc;
    }

    public String getFof() {
        return fof;
    }

    public void setFof(String fof) {
        this.fof = fof;
    }

    public String getFot() {
        return fot;
    }

    public void setFot(String fot) {
        this.fot = fot;
    }

    public String getHrt() {
        return hrt;
    }

    public void setHrt(String hrt) {
        this.hrt = hrt;
    }

    public String getDisrupt() {
        return disrupt;
    }

    public void setDisrupt(String disrupt) {
        this.disrupt = disrupt;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getAircraftNo() {
        return aircraftNo;
    }

    public void setAircraftNo(String aircraftNo) {
        this.aircraftNo = aircraftNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFpd() {
        return fpd;
    }

    public void setFpd(String fpd) {
        this.fpd = fpd;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAwake() {
        return awake;
    }

    public void setAwake(String awake) {
        this.awake = awake;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getWhatDidYouDo() {
        return whatDidYouDo;
    }

    public void setWhatDidYouDo(String whatDidYouDo) {
        this.whatDidYouDo = whatDidYouDo;
    }

    public String getWhatCouldBeDone() {
        return whatCouldBeDone;
    }

    public void setWhatCouldBeDone(String whatCouldBeDone) {
        this.whatCouldBeDone = whatCouldBeDone;
    }

    public String getOtherComment() {
        return otherComment;
    }

    public void setOtherComment(String otherComment) {
        this.otherComment = otherComment;
    }

    public String getFlightdecknap() {
        return flightdecknap;
    }

    public void setFlightdecknap(String flightdecknap) {
        this.flightdecknap = flightdecknap;
    }

    public String getHowYouFelt() {
        return howYouFelt;
    }

    public void setHowYouFelt(String howYouFelt) {
        this.howYouFelt = howYouFelt;
    }

}