package com.fpd.core;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.test.TestEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/1.
 */
public class TestAction {

    private Context mContext;

    public TestAction(Context context){
        mContext = context;
    }

    public void test(String name, String password,final CallBackListener<TestEntity> listener){
        Map<String,String> requestParam = new HashMap<>();
        requestParam.put("username",name);
        requestParam.put("password",password);

        SDApi.post(mContext, "http://192.168.23.2:8000/signup/", requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                  Log.d("response",response);
                if (listener !=null && response !=null){
                    CoreResponse<TestEntity> re = JSON.parseObject(response,new TypeReference<CoreResponse<TestEntity>>(){});
                    if (re.isSuccess()){
                        listener.onSuccess(re.getResult());
                    }else{
                        listener.onFailure(re.getErrorMessage());
                    }
                }
            }
        });
    }

    public  TestEntity jsonTest(String response){
        CoreResponse<TestEntity> re = JSON.parseObject(response,new TypeReference<CoreResponse<TestEntity>>(){});
        return re.getResult();
    }
}
