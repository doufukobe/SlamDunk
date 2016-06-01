package com.fpd.slamdunk.bussiness.test;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fpd.api.callback.CallBackListener;
import com.fpd.core.TestAction;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.test.TestEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

public class TestActivity extends CommenActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        TextView  tx = (TextView) findViewById(R.id.result);
        TestEntity te = new TestEntity();
        te.setName("fpd");
        te.setPassword("123456");
        CoreResponse<TestEntity> coreResponse = new CoreResponse<>();
        coreResponse.setErrorCode("0");
        coreResponse.setErrorMessage("111111");
        coreResponse.setResult(te);
        String j = JSON.toJSONString(coreResponse);
        Log.d("response",j);

        TestAction ta = new TestAction(this);
        TestEntity e = ta.jsonTest(j);
        Log.d("e",e.getName()+e.getPassword());

        ta.test("fpd", new CallBackListener<TestEntity>() {
            @Override
            public void onSuccess(TestEntity result) {
                //页面渲染
            }

            @Override
            public void onFailure(String Message) {
                //错误提示
            }
        });



    }
}