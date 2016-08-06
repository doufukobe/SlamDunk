package com.fpd.core.shake;

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
import com.fpd.model.shake.StartStateEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solo on 2016/6/25.
 */
public class ShakeAction
{
    private Context mContext;
    public ShakeAction(Context context)
    {
        this.mContext=context;
    }

    public void quickStart(String userId,String hasEquipment,double latitude,double longitude,
                           final CallBackListener<String> listener)
    {
        Map<String,String> requestParams=new HashMap<>();
        requestParams.put("userId",userId);
        requestParams.put("hasEquipment",hasEquipment);
        requestParams.put("addressLatitude",latitude+"");
        requestParams.put("addressLongitude",longitude+"");
        Log.i("TAG", "quickStart---latitude=" + latitude + "  longitude=" + longitude + "  hasEquipment=" + hasEquipment);
        SDApi.post(mContext, Config.headUrl+ URLContans.QUICKSTART, requestParams,
                new SDApiResponse<String>()
        {
            @Override
            public void onSuccess(String response)
            {
                if(listener!=null && response!=null)
                {
                    CoreResponse<String> re=null;
                    try
                    {
                        re = JSON.parseObject(response,
                                new TypeReference<CoreResponse<String>>()
                                {
                                });
                    }catch (Exception e){}
                    if(re!=null)
                    {
                        if (re.isSuccess())
                        {
                            listener.onSuccess(null);
                        } else
                        {
                            listener.onFailure(re.getErrorMessage());
                        }
                    }
                }
            }
        });
    }

    public void quickState(String userId,final CallBackListener<StartStateEntity> listener)
    {
        Map<String,String> requestParams=new HashMap<>();
        requestParams.put("userId", userId);
        SDApi.post(mContext, Config.headUrl+URLContans.QUICKSTARTE, requestParams,
                new SDApiResponse<String>()
        {
            @Override
            public void onSuccess(String response)
            {
                if(listener!=null && response!=null)
                {
                    CoreResponse<StartStateEntity> re=null;
                    try
                    {
                        re = JSON.parseObject(response,
                                new TypeReference<CoreResponse<StartStateEntity>>()
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

    public void quickPublish(String userId,long actTime,double addressLatitude,double
            addressLongitude,String addressInfo,int hasAccept,final CallBackListener<String> listener)
    {
        Map<String,String> requestParams=new HashMap<>();
        requestParams.put("userId",userId);
        requestParams.put("actTime",actTime+"");
        requestParams.put("addressLatitude",addressLatitude+"");
        requestParams.put("addressLongitude",addressLongitude+"");
        requestParams.put("addressInfo",addressInfo);
        requestParams.put("hasAccept",hasAccept+"");
        Log.i("TAG",requestParams+"");
        SDApi.post(mContext, Config.headUrl + URLContans.QUICKPUBLISH, requestParams,
                new SDApiResponse<String>()
                {
                    @Override
                    public void onSuccess(String response)
                    {
                        if(listener!=null && response!=null)
                        {
                            CoreResponse<String> re=null;
                            try
                            {
                                re = JSON.parseObject(response,
                                        new TypeReference<CoreResponse<String>>()
                                        {
                                        });
                            }catch (Exception e){}
                            if(re!=null)
                            {
                                if (re.isSuccess())
                                {
                                    listener.onSuccess(null);
                                } else
                                {
                                    listener.onFailure(re.getErrorMessage());
                                }
                            }
                        }
                    }
                });
    }

    public void quickAccept(String actId,String userId,int isAccept,
                            final CallBackListener<String> listener)
    {
        Map<String,String> requestParams=new HashMap<>();
        Log.i("TAG",actId+" "+userId+" "+isAccept);
        requestParams.put("actId", actId);
        requestParams.put("userId",userId);
        requestParams.put("hasAccept",isAccept+"");
        Log.i("TAG","quickAccept------actId="+actId+" userId="+userId);
        SDApi.post(mContext, Config.headUrl + URLContans.QUICKACCEPT, requestParams,
                new SDApiResponse<String>()
        {
            @Override
            public void onSuccess(String response)
            {
                if(listener!=null && response!=null)
                {
                    CoreResponse<String> re=null;
                    try
                    {
                        re = JSON.parseObject(response,
                                new TypeReference<CoreResponse<String>>()
                                {
                                });
                    }catch (Exception e){}
                    if(re!=null)
                    {
                        if (re.isSuccess())
                        {
                            listener.onSuccess(null);
                        } else
                        {
                            listener.onFailure(re.getErrorMessage());
                        }
                    }
                }
            }
        });
    }
}
