package com.fpd.core.register;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.config.URLContans;
import com.fpd.basecore.util.StyleCheckUtil;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.login.LREntity;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by solo on 2016/6/4.
 */
public class RegisterAction
{

    private Context mContext;
    public RegisterAction(Context context)
    {
        this.mContext= context;
    }

    public void action(final String name, final String password,final CallBackListener<LREntity> listener)
    {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StyleCheckUtil.checkStringStyle(name, StyleCheckUtil.LOGIN_REGEX)) {
            Toast.makeText(mContext, "用户名格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.length()<StyleCheckUtil.USERNAME_MIN_LENGTH || name.length()>StyleCheckUtil.USERNAME_MAX_LENGTH){
            Toast.makeText(mContext, "用户名长度不能小于6，大于20", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkPassword(password)) {
            return;
        }
        Map<String,String> requestParams=new HashMap<String,String>();
        requestParams.put("userName",name);
        requestParams.put("passWord",password);
        requestParams.put("registerId", JPushInterface.getRegistrationID(mContext));
        SDApi.post(mContext, Config.headUrl+ URLContans.REGISTER, requestParams, new SDApiResponse<String>()
        {
            @Override
            public void onSuccess(String response)
            {
                if (listener != null && response != null)
                {
                    CoreResponse<LREntity> re=null;
                    try
                    {
                        re = JSON.parseObject(response, new TypeReference<CoreResponse<LREntity>>()
                        {
                        });
                    }catch (Exception e){}
                    if(re!=null)
                    {
                        if (re.isSuccess())
                        {
                            listener.onSuccess(re.getResult());
                        } else
                        {
                            listener.onFailure(re.getErrorMessage());
                        }
                    }
                }
            }
        });


    }

    private boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        int passwordLen = StyleCheckUtil.count(password);
        if (passwordLen < StyleCheckUtil.PASSWORD_MIN_LENGTH || passwordLen > StyleCheckUtil.PASSWORD_MAX_LENGTH) {
            Toast.makeText(mContext, "密码长度不能小于8，大于16", Toast.LENGTH_SHORT).show();
            return false;
        }
        switch (StyleCheckUtil.checkPassword(password)) {
            case -1:
                Toast.makeText(mContext, "输入了非法字符", Toast.LENGTH_SHORT).show();
                return false;
            case 0:
                Toast.makeText(mContext, "密码应至少包含数字/字母/符号中的两种", Toast.LENGTH_SHORT).show();
                return false;
            default:
                break;
        }
        return true;
    }
}
