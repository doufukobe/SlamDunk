package com.fpd.core.thum;

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
import com.fpd.model.thum.ThumEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/18.
 */
public class ThumAction {

    private Context context;

    public ThumAction(Context context){
        this.context = context;
    }

    public void  thumUp(String userId, final CallBackListener<ThumEntity> listener){
        Map<String,String> requestParams = new HashMap<>();
        requestParams.put("userId",userId);

        SDApi.post(context, Config.headUrl + URLContans.THUMUP, requestParams, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response!=null && listener !=null){
                    Log.d("response",response);
                    CoreResponse<ThumEntity> coreResponse=null;
                    try
                    {
                        coreResponse = JSON.parseObject(response, new TypeReference<CoreResponse<ThumEntity>>()
                        {
                        });
                    }catch (Exception e){}
                    if(coreResponse!=null)
                    {
                        if (coreResponse.isSuccess())
                        {
                            listener.onSuccess(coreResponse.getResult());
                        } else
                        {
                            listener.onFailure(coreResponse.getErrorMessage());
                        }
                    }
                }
            }
        });
    }

}
