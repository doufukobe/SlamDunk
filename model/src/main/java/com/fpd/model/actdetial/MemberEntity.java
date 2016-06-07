package com.fpd.model.actdetial;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/7.
 */
public class MemberEntity implements Serializable {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
