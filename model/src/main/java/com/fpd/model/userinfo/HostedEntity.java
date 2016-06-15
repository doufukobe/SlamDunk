package com.fpd.model.userinfo;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/11.
 */
public class HostedEntity implements Serializable {
    private int actId;
    private String actName;
    private long actTime;
    private String actState;
    private int curPeopleNum;

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

    public String getActState() {
        return actState;
    }

    public void setActState(String actState) {
        this.actState = actState;
    }

    public int getCurPeopleNum() {
        return curPeopleNum;
    }

    public void setCurPeopleNum(int curPeopleNum) {
        this.curPeopleNum = curPeopleNum;
    }
}
