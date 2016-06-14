package com.fpd.api.commen.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.fpd.api.net.VolleyController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by t450s on 2016/6/1.
 */
public class SDStringRequest extends StringRequest {

    private Priority mPriority = Priority.NORMAL;
    private Map<String,String> requestParams;

    public SDStringRequest(int method, Map<String,String> requestParams,String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.requestParams = requestParams;
        setRetryPolicy(new DefaultRetryPolicy(3000,0,1));
    }

    public SDStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public SDStringRequest(String url,Map<String,String> requestParams, Response.Listener<String> listener, Response.ErrorListener errorListener){
       this(Method.POST,requestParams,url,listener,errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (this.requestParams !=null){
            return this.requestParams;
        }
        return super.getParams();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers,""));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        if (VolleyController.getInstance().progressDialog != null)
            VolleyController.getInstance().progressDialog.hideProgressDialog();
        super.deliverResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (VolleyController.getInstance().progressDialog != null) {
            VolleyController.getInstance().progressDialog.hideProgressDialog();
        }
        super.deliverError(error);
    }
}
