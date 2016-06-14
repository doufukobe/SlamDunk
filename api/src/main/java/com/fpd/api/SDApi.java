package com.fpd.api;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.fpd.api.commen.request.MultipartRequest;
import com.fpd.api.commen.request.SDStringRequest;
import com.fpd.api.net.VolleyController;
import com.fpd.basecore.NetWorkUtil;

import java.io.File;
import java.util.Map;

/**
 * Created by t450s on 2016/6/1.
 */
public class SDApi {

    public static void post(Context context,String url,Map<String,String> requestParams,SDApiResponse<String> apiResponse){
        if(NetWorkUtil.isNetworkConnected(context)){
            if (NetWorkUtil.isNetworkAvailable(context)){
                SDStringRequest request = new SDStringRequest(url,requestParams,apiResponse.successListener(),apiResponse.errorListener());
                VolleyController.getInstance().addToRequestQueueWithCache(request,context);
            }else{
                apiResponse.OnError(new NoConnectionError());
            }
        }else{
            apiResponse.OnError(new NoConnectionError());
        }
    }

    public static void post(Context context,String url,Map<String,String> requestParams,SDApiResponse<String> apiResponse,boolean showProgress){
        if(NetWorkUtil.isNetworkConnected(context)){
            if (NetWorkUtil.isNetworkAvailable(context)){
                SDStringRequest request = new SDStringRequest(url,requestParams,apiResponse.successListener(),apiResponse.errorListener());
                VolleyController.getInstance().addToRequestQueueWithCache(request,context,showProgress);
            }else{
                apiResponse.OnError(new NoConnectionError());
            }
        }else{
            apiResponse.OnError(new NoConnectionError());
        }
    }

    public static void upFile(Context context,String url,Map<String,String> requestParams,SDApiResponse<String> apiResponse,
                              String filePartName,File file){
        if(NetWorkUtil.isNetworkConnected(context)){
            if (NetWorkUtil.isNetworkAvailable(context)){
                MultipartRequest multipartRequest = new MultipartRequest(url,apiResponse.errorListener(),apiResponse.successListener()
                ,filePartName,file,requestParams);
                VolleyController.getInstance().addToRequestQueueWithCache(multipartRequest,context);
            }else{
                apiResponse.OnError(new NoConnectionError());
            }
        }else{
            apiResponse.OnError(new NoConnectionError());
        }
    }

}
