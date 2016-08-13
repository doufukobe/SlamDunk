package com.fpd.model.shake;

import java.io.Serializable;

/**
 * Created by solo on 2016/6/26.
 */
public class StartStateEntity implements Serializable
{
    private int startState;
    private int actId;
    public void setStartState(int startState)
    {
        this.startState=startState;
    }

    public int getStartState()
    {
        return startState;
    }

    public void setActId(int actId)
    {
        this.actId=actId;
    }

    public int getActId()
    {
        return actId;
    }
}
