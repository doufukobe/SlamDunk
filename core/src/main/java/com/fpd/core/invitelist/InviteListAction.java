package com.fpd.core.invitelist;

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
import com.fpd.model.invite.InviteListEntity;
import com.fpd.model.invite.inviteEntityList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t450s on 2016/6/3.
 */
public class InviteListAction {

    private Context context;
    private List<InviteListEntity>  testList;
    private InviteListEntity type1;
    private InviteListEntity type2;
    public InviteListAction(Context context){
        this.context = context;
        testList = new ArrayList<>();
        testInit();
    }

    public void getInviteList(final double latitude,double longitude, final CallBackListener<List<InviteListEntity>> listener){
//        testList.add(type1);
//        listener.onSuccess(testList);
//        Log.d("latitude", latitude + "");
//        Log.d("longitude",longitude+"");
        final Map<String,String> requestParam = new HashMap<>();
        requestParam.put("addressLongitude",longitude+"");
        requestParam.put("addressLatitude",latitude+"");
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
        });
    }


    private void testInit(){
        type1 = new InviteListEntity();
        type2 = new InviteListEntity();
        type1.setActId(1);
        type1.setActName("南邮3V3街头争霸赛");
        type1.setActOriginator("doufukobe");
        type1.setActTime(System.currentTimeMillis() + 1000 * 60 * 60 * 3);
        type1.setAddressDist("小于500m");
        type1.setActImg("http://img2.imgtn.bdimg.com/it/u=716793159,1538112665&fm=21&gp=0.jpg");

        type2.setActId(2);
        type2.setActName("南邮南操场随便玩玩");
        type2.setActOriginator("篮球圣手");
        type2.setActTime(System.currentTimeMillis() + 1000 * 60 * 60 * 2);
        type2.setAddressDist("小于800m");
        type2.setActImg("");
        testList.add(type1);
        testList.add(type2);
    }
}
