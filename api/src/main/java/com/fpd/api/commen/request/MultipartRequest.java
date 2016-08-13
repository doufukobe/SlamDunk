package com.fpd.api.commen.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fpd.api.net.VolleyController;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/8.
 */
public class MultipartRequest extends Request<String> {

    private MultipartEntity entity = new MultipartEntity();
    private Response.Listener<String> mListener ;
    private String filePartName;
    private File upLoadFile;
    private Map<String,String> mParams;


    public MultipartRequest(String url,Response.ErrorListener errorListener,Response.Listener<String> listener,
                           String filePartName,File file,Map<String,String> params){
        super(Method.POST,url,errorListener);
        this.filePartName = filePartName;
        upLoadFile = file;
        mListener = listener;
        mParams =params;
        buildMultipartEntity();
    }


    private void buildMultipartEntity() {
        if (upLoadFile !=null){
            entity.addPart("file",new FileBody(upLoadFile));
        }
        try {
            if (mParams != null && mParams.size() > 0) {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    entity.addPart(
                            entry.getKey(),
                            new StringBody(entry.getValue(), Charset
                                    .forName("UTF-8")));
                }
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }


        return headers;
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
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

        mListener.onResponse(response);
        if (VolleyController.getInstance().progressDialog != null)
            VolleyController.getInstance().progressDialog.hideProgressDialog();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams!=null)
            return mParams;
        return super.getParams();
    }

    @Override
    public void deliverError(VolleyError error) {
        if (VolleyController.getInstance().progressDialog != null) {
            VolleyController.getInstance().progressDialog.hideProgressDialog();
        }
        super.deliverError(error);
    }
}
