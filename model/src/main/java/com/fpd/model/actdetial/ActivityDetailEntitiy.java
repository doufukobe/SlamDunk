package com.fpd.model.actdetial;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t450s on 2016/6/6.
 */
public class ActivityDetailEntitiy implements Serializable {
    private String actName;
    private long actTime;
    private String addressInfo;
    private int maxPeopleNum;
    private int minPeopleNum;
    private int curPeopleNum;
    private boolean hasEquipment;
    private String actInfo;
    private String actOriginatorName;
    private String addressDist;
    private List<MemberEntity> memberList;
    private double addressLongitude;
    private double addressLatitude;
    private String actImg;

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


    public String getActInfo() {
        return actInfo;
    }

    public void setActInfo(String actInfo) {
        this.actInfo = actInfo;
    }


    public String getAddressDist() {
        return addressDist;
    }

    public void setAddressDist(String addressDist) {
        this.addressDist = addressDist;
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

    public List<MemberEntity> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberEntity> memberList) {
        this.memberList = memberList;
    }

    public boolean isHasEquipment() {
        return hasEquipment;
    }

    public void setHasEquipment(boolean hasEquipment) {
        this.hasEquipment = hasEquipment;
    }

    public int getCurPeopleNum() {
        return curPeopleNum;
    }

    public void setCurPeopleNum(int curPeopleNum) {
        this.curPeopleNum = curPeopleNum;
    }

    public String getActOriginatorName() {
        return actOriginatorName;
    }

    public void setActOriginatorName(String actOriginatorName) {
        this.actOriginatorName = actOriginatorName;
    }

    public String getActImg() {
        return actImg;
    }

    public void setActImg(String actImg) {
        this.actImg = actImg;
    }
}
