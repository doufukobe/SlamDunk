package com.fpd.core.deleteact;

import android.content.Context;
import android.telecom.Call;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.URICodec;
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
 * Created by t450s on 2016/6/12.
 */
public class DeleteAction {

    private Context context;

    public DeleteAction(Context context){
        this.context = context;
    }

    public void deleteAct(String userId,String actId, final CallBackListener<SuccessEntity> listener){
        Map<String,String> requestParams = new HashMap<>();

        requestParams.put("userId",userId);
        requestParams.put("actId",actId);

        SDApi.post(context, Config.headUrl + URLContans.DELETEACT, requestParams, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response !=null && listener !=null){
                    CoreResponse<SuccessEntity> coreResponse = JSON.parseObject(response,new TypeReference<CoreResponse<SuccessEntity>>(){});
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
