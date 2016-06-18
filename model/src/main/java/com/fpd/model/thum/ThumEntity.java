package com.fpd.model.thum;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/18.
 */
public class ThumEntity implements Serializable{

   private int userLiked;

    public int getUserLiked() {
        return userLiked;
    }

    public void setUserLiked(int userLiked) {
        this.userLiked = userLiked;
    }
}
