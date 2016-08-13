package com.fpd.core.signout;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.config.URLContans;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.success.SuccessEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/18.
 */
public class SignOutAction {

    Context context;

    public SignOutAction(Context context){
        this.context = context;
    }


    public void signout(String userId, final CallBackListener<SuccessEntity> listener){
        final Map<String,String> requestParam = new HashMap<>();
        requestParam.put("userId",userId);

        SDApi.post(context, Config.headUrl + URLContans.SIGNOUT, requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response !=null&&listener!=null){
                    CoreResponse<SuccessEntity> coreResponse=null;
                    try
                    {
                        coreResponse = JSON.parseObject(response, new TypeReference<CoreResponse<SuccessEntity>>()
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
        },false);
    }
}
