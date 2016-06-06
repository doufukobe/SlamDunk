package com.fpd.model.actdetial;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/6.
 */
public class ActivityDetailEntitiy implements Serializable {
    private String actName;
    private long actTime;
    private String addressInfo;
    private int maxPeopleNum;
    private int minPeopleNum;
    private String hasEquipment;
    private String actInfo;
    private String actOriginator;
    private String addressDist;
    private String memberList;
    private double addressLongitude;
    private double addressLatitude;

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public long getActTime() {
        return actTime;
    }

    public void setActTime(long actTime) {
        this.actTime = actTime;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public int getMaxPeopleNum() {
        return maxPeopleNum;
    }

    public void setMaxPeopleNum(int maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public int getMinPeopleNum() {
        return minPeopleNum;
    }

    public void setMinPeopleNum(int minPeopleNum) {
        this.minPeopleNum = minPeopleNum;
    }

    public String getHasEquipment() {
        return hasEquipment;
    }

    public void setHasEquipment(String hasEquipment) {
        this.hasEquipment = hasEquipment;
    }

    public String getActInfo() {
        return actInfo;
    }

    public void setActInfo(String actInfo) {
        this.actInfo = actInfo;
    }

    public String getActOriginator() {
        return actOriginator;
    }

    public void setActOriginator(String actOriginator) {
        this.actOriginator = actOriginator;
    }

    public String getAddressDist() {
        return addressDist;
    }

    public void setAddressDist(String addressDist) {
        this.addressDist = addressDist;
    }

    public String getMemberList() {
        return memberList;
    }

    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }

    public double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }
}
