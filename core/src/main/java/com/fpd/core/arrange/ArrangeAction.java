package com.fpd.core.arrange;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.arrange.ArrangeEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/4.
 */
public class ArrangeAction {

    private Context context;

    public ArrangeAction(Context context){
        this.context = context;
    }

    public void setArrange(String actName,String timeLong,String itemNum,String minNum
        ,String hasBall,String introduce,double latitude,double longtitude,String addressInfo, final CallBackListener<ArrangeEntity> listener){

        final Map<String,String> requestParam = new HashMap<>();
        requestParam.put("addressLongitude",longtitude+"");
        requestParam.put("addressLatitude",latitude+"");
        requestParam.put("actName",actName);
        requestParam.put("actTime",timeLong);
        requestParam.put("addressInfo",addressInfo);
        requestParam.put("maxPeople",itemNum);
        requestParam.put("minPeople",minNum);
        requestParam.put("hasEquipment",hasBall);
        requestParam.put("actInfo",introduce);
        SDApi.post(context, "", requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response != null && listener != null) {
                    CoreResponse<ArrangeEntity> coreResponse = JSON.parseObject(response, new TypeReference<CoreResponse<ArrangeEntity>>() {
                    });
                    if (coreResponse.isSuccess()) {
                        listener.onSuccess(coreResponse.getResult());
                    } else {
                        listener.onFailure(coreResponse.getErrorMessage());
                    }
                }
            }
        });

    }

}
