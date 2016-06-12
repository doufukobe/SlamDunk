package com.fpd.model.userinfo;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/11.
 */
public class JoinedEntity implements Serializable {
    private  int  actId;
    private String actOriginator;
    private long actTime;
    private String actState;

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public String getActOriginator() {
        return actOriginator;
    }

    public void setActOriginator(String actOriginator) {
        this.actOriginator = actOriginator;
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
}
