package com.fpd.slamdunk.shake;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.application.BaseApplication;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.core.shake.ShakeAction;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.gc.materialdesign.views.ButtonRectangle;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by solo on 2016/6/25.
 */
public class ShakeChoseActivity extends CommenActivity implements View.OnClickListener
{

    private View mBack;
    private TextView mTimeSet;
    private ButtonRectangle mSubmit;
    private ButtonRectangle mCancel;
    private ShakeAction shakeAction;

    private MapView baiduMap;
    private BaiduMap locationMap;
    private LocationClient arrangeClient;
    private GeoCoder mSearch;
    private PoiSearch poiSearch;
    private BitmapDescriptor basketPlace;

    private TimePickerDialog mDialogMonthDayHourMinute;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private long actTime;
    private double locationLa;
    private double locationLo;
    private String addressInfo;

    private String[] tip={"活动取消成功","活动发起成功"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_choose);

        shakeAction=new ShakeAction(this);

        initViews();
        initEvents();
        initTimePickDialog();
        initLocation();
    }

    private void initViews()
    {
        baiduMap=(MapView)findViewById(R.id.id_shake_choose_mapview);
        locationMap=baiduMap.getMap();
        mBack=findViewById(R.id.id_shake_choose_back_ly);
        mTimeSet=(TextView)findViewById(R.id.id_shake_choose_time_set);
        mSubmit=(ButtonRectangle)findViewById(R.id.id_shake_choose_accept);
        mCancel=(ButtonRectangle)findViewById(R.id.id_shake_choose_refuse);
    }

    private void initEvents()
    {
        mBack.setOnClickListener(this);
        mTimeSet.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }



    private void initTimePickDialog()
    {
        mDialogMonthDayHourMinute = new TimePickerDialog.Builder()
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setCallBack(new MyDateSetlistener())
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setCurrentMillseconds(System.currentTimeMillis()+3600*2*1000)
                .setThemeColor(getResources().getColor(R.color.colormain))
                .setWheelItemTextSize(14)
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colormain))
                .build();
    }

    private void initLocation()
    {
        arrangeClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(10000);
        option.setOpenGps(true);
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        arrangeClient.setLocOption(option);
        arrangeClient.registerLocationListener(new MyLocationListener());
        arrangeClient.start();

        basketPlace = BitmapDescriptorFactory.fromResource(R.mipmap.bkplayground);

        locationMap.setOnMapStatusChangeListener(new MyMapStatusChangeListener());
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new GeoListener());
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(new PoiListener());
    }

    private class MyDateSetlistener implements OnDateSetListener
    {
        @Override
        public void onDateSet(TimePickerDialog timePickerView, long millseconds)
        {
            actTime=millseconds/1000;
            mTimeSet.setText(getDateToString(millseconds));
        }

        public String getDateToString(long time) {
            Date d = new Date(time);
            return sf.format(d);
        }
    }

    private class MyLocationListener implements BDLocationListener
    {

        @Override
        public void onReceiveLocation(BDLocation bdLocation)
        {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
                    bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                Log.d("latitude",bdLocation.getLatitude()+"");
                Log.d("longitude", bdLocation.getLongitude() + "");

                final LatLng cenpt =  new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(cenpt)
                        .zoom(16)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                locationMap.setMapStatus(mMapStatusUpdate);
                arrangeClient.stop();
                poiSearch.searchNearby(new PoiNearbySearchOption().keyword("篮球场")
                                            .location(cenpt).radius(2000));
                locationLa =bdLocation.getLatitude() ;
                locationLo = bdLocation.getLongitude();
                addressInfo = bdLocation.getLocationDescribe();

            }else{
                Toast.makeText(ShakeChoseActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //移动地图时
    private class MyMapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener
    {

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus)
        {
        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus)
        {
        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus)
        {
            float density = BaseApplication.getDensity();
            int x = BaseApplication.getScreenWidth()/2;
            int y = (int)(150*density);
            android.graphics.Point point = new android.graphics.Point(x,y);
            LatLng center = locationMap.getProjection().fromScreenLocation(point);

            locationLa =center.latitude ;
            locationLo = center.longitude;

            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(center));

            poiSearch.searchNearby(new PoiNearbySearchOption().keyword("篮球场")
                    .location(center).radius(2000));
        }
    }

    private class GeoListener implements OnGetGeoCoderResultListener
    {

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult)
        {
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult)
        {
            addressInfo = reverseGeoCodeResult.getPoiList().get(0).address+ "  "+ reverseGeoCodeResult.getAddressDetail().city + "  "+
                    reverseGeoCodeResult.getAddressDetail().district +"  "+ reverseGeoCodeResult.getAddressDetail().street +
                    reverseGeoCodeResult.getAddressDetail().streetNumber;
            Log.i("TAG", "address====="+addressInfo);
        }
    }

    private class PoiListener implements OnGetPoiSearchResultListener
    {

        @Override
        public void onGetPoiResult(PoiResult poiResult)
        {
            locationMap.clear();
            List<PoiInfo> rs = poiResult.getAllPoi();
            if(rs !=null)
            {
                for(PoiInfo i:rs)
                {
                    LatLng c = new LatLng(i.location.latitude,i.location.longitude);
                    OverlayOptions option = new MarkerOptions()
                            .position(c)
                            .icon(basketPlace)
                            .draggable(true);
                    locationMap.addOverlay(option);
                }
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult)
        {
        }
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_shake_choose_back_ly:
                finish();
                break;
            case R.id.id_shake_choose_time_set:
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(),
                        "month_day_hour_minute");
                break;
            case R.id.id_shake_choose_accept:
                submit(1);
                break;
            case R.id.id_shake_choose_refuse:
                submit(-1);
                break;
        }
    }

    private void submit(final int hasAccept)
    {

        shakeAction.quickPublish(Config.userId, actTime, locationLa, locationLo,
                addressInfo, hasAccept, new CallBackListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        SDDialog sdDialog = new SDDialog(ShakeChoseActivity.this, tip[(hasAccept+1)/2]
                                ,new dialogCallback());
                        sdDialog.show();
                    }

                    @Override
                    public void onFailure(String Message)
                    {
                        Toast.makeText(ShakeChoseActivity.this, Message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class dialogCallback implements SDDialog.Callback{

        @Override
        public void sureCallBack() {
           finish();
        }

        @Override
        public void cancelCallBack() {

        }
    }
}
