package com.fpd.model.acthead;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t450s on 2016/6/15.
 */
public class MyActDetailEntity implements Serializable {
    private String actName;
    private long actTime;
    private String addressInfo;
    private int curPeopleNum;
    private List<MyActHeadEntity> memberList;
    private String actInfo;

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

    public int getCurPeopleNum() {
        return curPeopleNum;
    }

    public void setCurPeopleNum(int curPeopleNum) {
        this.curPeopleNum = curPeopleNum;
    }



    public String getActInfo() {
        return actInfo;
    }

    public void setActInfo(String actInfo) {
        this.actInfo = actInfo;
    }

    public List<MyActHeadEntity> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MyActHeadEntity> memberList) {
        this.memberList = memberList;
    }
}
