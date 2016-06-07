package com.fpd.slamdunk.bussiness.test;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.fpd.api.callback.CallBackListener;
import com.fpd.core.TestAction;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.invite.InviteListEntity;
import com.fpd.model.invite.inviteEntityList;
import com.fpd.model.test.TestEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

import java.util.ArrayList;

public class TestActivity extends CommenActivity{
    MapView mapView;
    BaiduMap baiduMap;
    LocationClient mLocationClient = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.test);

        TextView  tx = (TextView) findViewById(R.id.result);
        InviteListEntity te = new InviteListEntity();
        te.setActId(3);
        te.setActImg("naidu");
        te.setActTime(System.currentTimeMillis());
        te.setActOriginator("doufukobe");
        te.setAddressDist("300m");
        te.setActName("nanyou");
        te.setMaxPeopleNum(10);
        te.setCurPeopleNum(8);
        inviteEntityList x = new inviteEntityList();
        ArrayList<InviteListEntity> list =  new ArrayList<InviteListEntity>();
        list.add(te);
        list.add(te);
        x.setActList(list);
        CoreResponse<inviteEntityList> coreResponse = new CoreResponse<>();
        coreResponse.setErrorCode("0");
        coreResponse.setErrorMessage("111111");
        coreResponse.setResult(x);
        String j = JSON.toJSONString(coreResponse);
        Log.d("response",j);
//
//        TestAction ta = new TestAction(this);
//
//        ta.test("fpdfpd","123456", new CallBackListener<TestEntity>() {
//            @Override
//            public void onSuccess(TestEntity result) {
//                //页面渲染
//                Log.d("Code",result.getCode());
//            }
//
//            @Override
//            public void onFailure(String Message) {
//                //错误提示
//            }
//        });

//        mapView = (MapView) findViewById(R.id.baidumap);
//        baiduMap = mapView.getMap();
//        baiduMap.setMyLocationEnabled(true);
//        mLocationClient = new LocationClient(getApplicationContext());
//
//        initLocation();
//        mLocationClient.start();
//        mLocationClient.registerLocationListener(new MyLocationListener(mLocationClient, baiduMap));
    }

    private void  initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}