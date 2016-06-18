package com.fpd.core.arrange;

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

    public void setArrange(final String actName, final long timeLong, final String itemNum, final String minNum
        ,String hasBall, final String introduce, final double latitude, final double longtitude,String addressInfo, final CallBackListener<ArrangeEntity> listener){

        final Map<String,String> requestParam = new HashMap<>();
        requestParam.put("userId",Config.userId);
        requestParam.put("addressLongitude",longtitude+"");
        requestParam.put("addressLatitude",latitude+"");
        requestParam.put("actName",actName);
        requestParam.put("actTime",timeLong+"");
        requestParam.put("addressInfo",addressInfo);
        requestParam.put("maxPeopleNum",itemNum);
        requestParam.put("minPeopleNum",minNum);
        requestParam.put("hasEquipment", hasBall);
        requestParam.put("actInfo", introduce);

        if (timeLong == 0){
            listener.onFailure("活动时间未选择，无法发起活动");
        }else if (latitude==0&&longtitude==0){
            listener.onFailure("定位失败，无法发起活动");
        }else if(actName.length() >50){
            listener.onFailure("活动名称太长，请重新修改");
        }else if(introduce.length() >255){
            listener.onFailure("活动简介太长，请重新修改");
        }else if(Integer.valueOf(minNum) > Integer.valueOf(itemNum)){
            listener.onFailure("最小人数不能大于目标人数");
        }else if(actName.isEmpty()){
            listener.onFailure("活动名称不能为空，请重新修改");
        }else{
            SDApi.post(context, Config.headUrl+ URLContans.PUBLISHACTIVITY, requestParam, new SDApiResponse<String>() {
                @Override
                public void onSuccess(String response) {

                    if (response != null && listener != null) {
                        Log.d("response",response);
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

}
