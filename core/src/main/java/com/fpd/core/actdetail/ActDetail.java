package com.fpd.core.actdetail;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.config.URLContans;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.actdetial.ActivityDetailEntitiy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/6.
 */
public class ActDetail {

    private Context context;

    public ActDetail(Context context){
        this.context = context;
    }

    public void getActivityDetail(String actId, final CallBackListener<ActivityDetailEntitiy> listener){

        Map<String,String> requestParams = new HashMap<>();
        requestParams.put("actId",actId);

        SDApi.post(context, Config.headUrl+ URLContans.GETACTIVITY, requestParams, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response !=null && listener !=null){
                    Log.d("response",response);
                    CoreResponse<ActivityDetailEntitiy> coreResponse = JSON.parseObject(response,new TypeReference<CoreResponse<ActivityDetailEntitiy>>(){});
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
