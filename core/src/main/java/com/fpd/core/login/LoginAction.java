package com.fpd.core.login;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.login.LREntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solo on 2016/6/4.
 */
public class LoginAction
{

    private Context mContext;
    public LoginAction(Context context)
    {
        this.mContext=context;
    }

    public void action(final String name, final String password,final CallBackListener<LREntity> listener)
    {
        Map<String ,String> requestParams=new HashMap<String,String>();
        requestParams.put("userName",name);
        requestParams.put("passWord",password);
        SDApi.post(mContext, "http://192.168.23.2:8000/signin/", requestParams, new SDApiResponse<String>()
        {
            @Override
            public void onSuccess(String response)
            {
                if(listener!=null && response!=null)
                {
                    CoreResponse<LREntity> re = JSON.parseObject(response, new TypeReference<CoreResponse<LREntity>>(){});
                    if(re.isSuccess())
                    {
                        listener.onSuccess(re.getResult());
                    }
                    else
                    {
                        listener.onFailure(re.getErrorMessage());
                    }
                }
            }
        });

    }
}
