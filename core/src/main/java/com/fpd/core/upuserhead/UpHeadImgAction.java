package com.fpd.core.upuserhead;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fpd.api.SDApi;
import com.fpd.api.SDApiResponse;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.config.URLContans;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.success.SuccessEntity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by t450s on 2016/6/15.
 */
public class UpHeadImgAction {

    private Context context;

    public UpHeadImgAction(Context context){
        this.context = context;
    }

    public void UpLoadImg(String userId,String filePartName,File file, final CallBackListener<SuccessEntity> listener){
        final Map<String,String> requestParams = new HashMap<>();
        requestParams.put("userId",userId);

        SDApi.upFile(context, Config.headUrl + URLContans.UPLOADHEADIMG, requestParams, new SDApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                if (response!=null && listener !=null){
                    CoreResponse<SuccessEntity> coreResponse = JSON.parseObject(response,new TypeReference<CoreResponse<SuccessEntity>>(){});
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
