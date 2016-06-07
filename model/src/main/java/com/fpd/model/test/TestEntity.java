package com.fpd.model.test;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/1.
 */
public class TestEntity implements Serializable {

//    private String code;
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
