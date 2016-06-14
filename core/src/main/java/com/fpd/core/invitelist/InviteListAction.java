package com.fpd.core.invitelist;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.VolleyError;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.api.net.ErrorHelper;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.config.URLContans;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.invite.InviteListEntity;
import com.fpd.model.invite.inviteEntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by t450s on 2016/6/3.
 */
public class InviteListAction {

    private Context context;
    private List<InviteListEntity>  testList;
    public InviteListAction(Context context){
        this.context = context;
        testList = new ArrayList<>();
    }

    public void getInviteList(final double latitude,double longitude, final CallBackListener<List<InviteListEntity>> listener){

        final Map<String,String> requestParam = new HashMap<>();
        requestParam.put("addressLongitude",longitude+"");
        requestParam.put("addressLatitude",latitude+"");
       // requestParam.put("registerId", JPushInterface.getRegistrationID(context));
        SDApi.post(context, Config.headUrl+ URLContans.GETACTIVITYLIST, requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response !=null && listener !=null){
                    Log.d("response",response);
                    CoreResponse<inviteEntityList> coreResponse = JSON.parseObject(response,new TypeReference<CoreResponse<inviteEntityList>>(){});
                    if (coreResponse.isSuccess()){
                        listener.onSuccess(coreResponse.getResult().getActList());
                    }else{
                        listener.onFailure(coreResponse.getErrorMessage());
                    }
                }
            }

            @Override
            public void OnError(VolleyError error) {
                super.OnError(error);
                listener.onFailure(ErrorHelper.getMessage(error));
            }
        },false);
    }
}
