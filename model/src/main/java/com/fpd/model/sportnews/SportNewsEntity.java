package com.fpd.model.sportnews;

import java.io.Serializable;

/**
 * Created by solo on 2016/6/5.
 */
public class SportNewsEntity implements Serializable
{
    public int code;
    public SportNewsEntityData data;

    @Override
    public String toString()
    {
        return "{"+"\n"+
                "code:"+code+"\n"+
                "data:"+data+"\n";
    }
}
