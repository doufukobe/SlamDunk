package com.fpd.core.joinsubmit;

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
import com.fpd.model.success.SuccessEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/6.
 */
public class JoinSubmitAction {

    private Context context;

    public JoinSubmitAction(Context context){
        this.context = context;
    }

    public void submit(String actId,String userId,String personInfo,String hasEquipment, final CallBackListener<SuccessEntity> listener){

        Map<String,String> requestParam = new HashMap<>();
        requestParam.put("actId",actId);
        requestParam.put("userId",userId);
        requestParam.put("personalInfo",personInfo);
        requestParam.put("hasEquipment",hasEquipment);

        SDApi.post(context, Config.headUrl+ URLContans.SUBMITJOIN, requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response !=null&&listener !=null){
                    Log.d("response",response);
                    CoreResponse<SuccessEntity> coreResponse=null;
                    try
                    {
                        coreResponse = JSON.parseObject(response, new TypeReference<CoreResponse<SuccessEntity>>()
                        {
                        });
                    }catch (Exception e)
                    {}
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
