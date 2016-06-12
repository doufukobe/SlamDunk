package com.fpd.model.userinfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by t450s on 2016/6/11.
 */
public class UserInfoEntity implements Serializable {
    private String userPetName;
    private int userAge;
    private String userSex;
    private String userPosition;
    private int userLiked;
    private List<HostedEntity> userHostedAct;
    private List<JoinedEntity> userJoinedAct;
    private String userHeadUrl;

    public String getUserPetName() {
        return userPetName;
    }

    public void setUserPetName(String userPetName) {
        this.userPetName = userPetName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public int getUserLiked() {
        return userLiked;
    }

    public void setUserLiked(int userLiked) {
        this.userLiked = userLiked;
    }

    public List<HostedEntity> getUserHostedAct() {
        return userHostedAct;
    }

    public void setUserHostedAct(List<HostedEntity> userHostedAct) {
        this.userHostedAct = userHostedAct;
    }

    public List<JoinedEntity> getUserJoinedAct() {
        return userJoinedAct;
    }

    public void setUserJoinedAct(List<JoinedEntity> userJoinedAct) {
        this.userJoinedAct = userJoinedAct;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }
}
