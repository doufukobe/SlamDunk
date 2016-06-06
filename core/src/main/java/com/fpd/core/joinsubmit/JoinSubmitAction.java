package com.fpd.core.joinsubmit;

import android.content.Context;

import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.model.arrange.ArrangeEntity;

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

    public void submit(String actId,String userId,String personInfo,String hasEquipment,CallBackListener<ArrangeEntity> listener){

        Map<String,String> requestParam = new HashMap<>();
        requestParam.put("actId",actId);
        requestParam.put("userId",userId);
        requestParam.put("personalInfo",personInfo);
        requestParam.put("hasEquipment",hasEquipment);

        SDApi.post(context, "", requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {

            }
        });

    }

}
