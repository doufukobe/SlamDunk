package com.fpd.model.acthead;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/15.
 */
public class MyActHeadEntity implements Serializable {

    private String headUrl;
    private String userName;
    private String userId;

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
