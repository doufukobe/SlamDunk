package com.fpd.core.myactdetail;

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
import com.fpd.model.acthead.MyActDetailEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/15.
 */
public class MyActDetailAction{

    private Context context;

    public MyActDetailAction(Context context){
            this.context = context;
    }


    public void getDetail(String actId,String actState, final CallBackListener<MyActDetailEntity> listener){

        Map<String,String> requestParams = new HashMap<>();
        requestParams.put("actId",actId);
        requestParams.put("actState",actState);
        SDApi.post(context, Config.headUrl+ URLContans.GETMYACTIVITY,requestParams,new SDApiResponse<String>(){

            @Override
            public void onSuccess(String response) {
                Log.d("response",response);
                if (response !=null && listener !=null){
                    CoreResponse<MyActDetailEntity> coreResponse = JSON.parseObject(response,new TypeReference<CoreResponse<MyActDetailEntity>>(){});
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
