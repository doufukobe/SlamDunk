package com.fpd.core.upactphoto;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.arrange.ArrangeEntity;
import com.fpd.model.success.SuccessEntity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/10.
 */
public class UpActPhotoAction {

    private Context context;

    public UpActPhotoAction(Context context){
        this.context = context;
    }

    public void UpLoadImg(String actId,String filePartName,File file, final CallBackListener<SuccessEntity> listener){
        Map<String,String> requestParam = new HashMap<>();
        requestParam.put("actId",actId);

        SDApi.upFile(context, "", requestParam, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response !=null && listener !=null){
                    CoreResponse<SuccessEntity> coreResponse = JSON.parseObject(response, new TypeReference<CoreResponse<SuccessEntity>>(){});
                    if (coreResponse.isSuccess()){
                        listener.onSuccess(coreResponse.getResult());
                    }else{
                        listener.onFailure(coreResponse.getErrorMessage());
                    }
                }
            }
        },filePartName,file);
    }

}
