package com.fpd.slamdunk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by t450s on 2016/6/1.
 */
public class CommenActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
    }
}
