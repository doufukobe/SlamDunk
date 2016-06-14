package com.fpd.api.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fpd.basecore.application.BaseApplication;
import com.fpd.basecore.dialog.SDProgressDialog;

/**
 * Created by t450s on 2016/6/1.
 */
public class VolleyController {

    private static volatile VolleyController sInstance;

    private RequestQueue requestQueue;

    public SDProgressDialog progressDialog;

    private VolleyController(){
        requestQueue = Volley.newRequestQueue(BaseApplication.getApplication());
    }

    public static VolleyController getInstance(){
        if (sInstance == null) {
            synchronized (VolleyController.class) {
                sInstance = new VolleyController();
            }
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    public <T> void addToRequestQueueWithCache(Request<T> request,Object TAG){
        addToRequestQueueWithCache(request,TAG,true);
    }

    public <T> void addToRequestQueueWithCache(Request<T> request,Object TAG,boolean showProgress){

        Context context = (Context)TAG;


        if (progressDialog == null)
            progressDialog = new SDProgressDialog(context);
        else{
            if (progressDialog.isShowing())
                progressDialog.hideProgressDialog();

            progressDialog = new SDProgressDialog(context);
        }

        if (showProgress)
            progressDialog.showProgressDialog();

        request.setTag(TAG);
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
