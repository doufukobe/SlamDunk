package com.fpd.core.userinfo;

import android.content.Context;
import android.telecom.Call;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.config.URLContans;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.userinfo.UserInfoEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/11.
 */
public class UserInfoAction {

    private Context context;

    public UserInfoAction(Context context){
        this.context = context;
    }

    public void GetUserInfo(String userId, final CallBackListener<UserInfoEntity> listener){
        Map<String,String> requestParam = new HashMap<>();
        requestParam.put("userId",userId);

        SDApi.post(context, Config.headUrl + URLContans.GETUSERINFO, requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response!=null && listener !=null){
                    CoreResponse<UserInfoEntity> coreResponse = JSON.parseObject(response, new TypeReference<CoreResponse<UserInfoEntity>>(){});
                    if (coreResponse.isSuccess()){
                        listener.onSuccess(coreResponse.getResult());
                    }else{
                        listener.onFailure(coreResponse.getErrorMessage());
                    }
                }
            }
        });
    }

}
