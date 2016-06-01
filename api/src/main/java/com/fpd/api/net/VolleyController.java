package com.fpd.api.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fpd.basecore.application.BaseApplication;

/**
 * Created by t450s on 2016/6/1.
 */
public class VolleyController {

    private static volatile VolleyController sInstance;

    private RequestQueue requestQueue;

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
        request.setTag(TAG);
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
