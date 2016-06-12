package com.fpd.model.myactlist;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/10.
 */
public class MyActListEntity implements Serializable {
    private String actName;
    private long actTime;
    private String actState;
    private int actId;


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

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }
}
