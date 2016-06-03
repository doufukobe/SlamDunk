package com.fpd.model.invite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t450s on 2016/6/3.
 */
public class inviteEntityList implements Serializable {
    private ArrayList<InviteListEntity>  actList;

    public ArrayList<InviteListEntity> getActList() {
        return actList;
    }

    public void setActList(ArrayList<InviteListEntity> actList) {
        this.actList = actList;
    }
}
