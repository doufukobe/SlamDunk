package com.fpd.model.invite;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/3.
 */
public class InviteListEntity implements Serializable {

    private int actId;
    private String actName;
    private String actOriginatorName;
    private long actTime;
    private String actImg;
    private String addressDist;
    private int curPeopleNum;
    private int maxPeopleNum;

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

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

    public String getAddressDist() {
        return addressDist;
    }

    public void setAddressDist(String addressDist) {
        this.addressDist = addressDist;
    }

    public int getCurPeopleNum() {
        return curPeopleNum;
    }

    public void setCurPeopleNum(int curPeopleNum) {
        this.curPeopleNum = curPeopleNum;
    }

    public int getMaxPeopleNum() {
        return maxPeopleNum;
    }

    public void setMaxPeopleNum(int maxPeopleNum) {
        this.maxPeopleNum = maxPeopleNum;
    }

    public String getActImg() {
        return actImg;
    }

    public void setActImg(String actImg) {
        this.actImg = actImg;
    }

    public String getActOriginatorName() {
        return actOriginatorName;
    }

    public void setActOriginatorName(String actOriginatorName) {
        this.actOriginatorName = actOriginatorName;
    }
}
