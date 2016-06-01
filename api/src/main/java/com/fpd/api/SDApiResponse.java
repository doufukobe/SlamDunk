package com.fpd.api;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fpd.api.net.ErrorHelper;
import com.fpd.basecore.application.BaseApplication;

/**
 * Created by t450s on 2016/6/1.
 */
public abstract  class SDApiResponse<T> {
    private Response.Listener<T> mListener;
    private Response.ErrorListener mErrorListener;

    public Response.Listener<T> successListener(){
        mListener = new Response.Listener<T>(){
            @Override
            public void onResponse(T response) {
                onSuccess(response);
            }
        };
        return mListener;
    }

    public Response.ErrorListener errorListener(){
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                OnError(error);
            }
        };
        return mErrorListener;
    }

    public abstract void onSuccess(T response);

    public void OnError(VolleyError error){
        Toast.makeText(BaseApplication.getApplication(), ErrorHelper.getMessage(error),Toast.LENGTH_SHORT).show();
    }





}
