package com.fpd.model.acthead;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/15.
 */
public class MyActHeadEntity implements Serializable {

    private String userImg;
    private String userName;
    private String userId;



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

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
