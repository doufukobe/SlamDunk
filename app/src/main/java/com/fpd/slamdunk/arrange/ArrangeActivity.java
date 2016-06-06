package com.fpd.slamdunk.arrange;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
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
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.application.BaseApplication;
import com.fpd.core.arrange.ArrangeAction;
import com.fpd.model.arrange.ArrangeEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.widget.WalkingRouteOverlay;
import com.gc.materialdesign.views.ButtonRectangle;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by t450s on 2016/6/4.
 */
public class ArrangeActivity extends CommenActivity {

    private TextView arrange_date;
    private TimePickerDialog mDialogMonthDayHourMinute;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ScrollView scrollView;

    private MapView baiduMap;
    private BaiduMap locationMap;
    private LocationClient arrangeClient;

    private ImageButton item_sub;
    private ImageButton item_add;
    private ImageButton min_sub;
    private ImageButton min_add;
    private TextView item_num;
    private TextView min_num;

    private ImageView center_Tip;
    private int tip_height;
    private ButtonRectangle startAct;
    private ArrangeAction arrangeAct;

    private EditText activeName;
    private long actTime;
    private CheckBox hasBall;
    private EditText introduce;

    private double locationLa;
    private double locationLo;
    private String addressInfo;

    private GeoCoder mSearch;
    private PoiSearch poiSearch;
    private BitmapDescriptor basketPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrange_layout);
        initView();
        setOnClick();
    }

    private void setOnClick(){
        arrange_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "month_day_hour_minute");
            }
        });

        baiduMap.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        item_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = Integer.valueOf(item_num.getText().toString());
                cur--;
                if (cur > 0)
                    item_num.setText(cur + "");
                else
                    item_num.setText("0");
            }
        });

        item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = Integer.valueOf(item_num.getText().toString());
                cur++;
                if (cur > 0)
                    item_num.setText(cur + "");
                else
                    item_num.setText("0");
            }
        });

        min_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = Integer.valueOf(min_num.getText().toString());
                cur--;
                if (cur > 0)
                    min_num.setText(cur + "");
                else
                    min_num.setText("0");
            }
        });

        min_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = Integer.valueOf(min_num.getText().toString());
                cur++;
                if (cur > 0)
                    min_num.setText(cur + "");
                else
                    min_num.setText("0");
            }
        });
        startAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = hasBall.isChecked()?"1":"0";
                arrangeAct.setArrange(activeName.getText().toString(), actTime + "", item_num.getText().toString()
                        , min_num.getText().toString(), s, introduce.getText().toString(), locationLa, locationLo
                        , addressInfo, new CallBackListener<ArrangeEntity>() {
                    @Override
                    public void onSuccess(ArrangeEntity result) {

                    }

                    @Override
                    public void onFailure(String Message) {

                    }
                });
            }
        });
    }

    private void  initView(){
        arrange_date = (TextView) findViewById(R.id.arrange_date);
        baiduMap = (MapView) findViewById(R.id.arrange_baidumap);
        locationMap = baiduMap.getMap();
        scrollView = (ScrollView) findViewById(R.id.arrange_scroll);
        activeName = (EditText) findViewById(R.id.arrange_name);

        item_sub = (ImageButton) findViewById(R.id.item_subtract_button);
        item_add = (ImageButton) findViewById(R.id.item_add_button);
        min_sub = (ImageButton) findViewById(R.id.jianshao_button);
        min_add = (ImageButton) findViewById(R.id.zengjia_button);
        item_num = (TextView) findViewById(R.id.max_num);
        min_num= (TextView) findViewById(R.id.min_num);

        center_Tip = (ImageView) findViewById(R.id.arrange_center_tip);
        startAct = (ButtonRectangle) findViewById(R.id.start_active);

        hasBall = (CheckBox) findViewById(R.id.arrange_hasBall);
        introduce = (EditText) findViewById(R.id.arrange_introduce_edit);
        initOther();
    }

    private void initOther(){
        mDialogMonthDayHourMinute = new TimePickerDialog.Builder()
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setCallBack(new MyDateSetlistener())
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.colormain))
                .setWheelItemTextSize(14)
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colormain))
                .build();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_gcoding);
        tip_height = bitmap.getHeight();
        center_Tip.setPadding(0, 0, 0, tip_height);
        center_Tip.setImageBitmap(bitmap);

        basketPlace = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);

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

        locationMap.setOnMapStatusChangeListener(new MyMapStatusChangeListener());

        arrangeAct = new ArrangeAction(this);

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new GeoListener());
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(new PoiListener());
    }

    private class MyDateSetlistener implements OnDateSetListener {

        @Override
        public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
            actTime = millseconds;
            arrange_date.setText(getDateToString(millseconds));
        }

        public String getDateToString(long time) {
            Date d = new Date(time);
            return sf.format(d);
        }
    }

    private class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
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
                poiSearch.searchNearby(new PoiNearbySearchOption().keyword("篮球场").location(cenpt).radius(2000));

            }else{
                Toast.makeText(ArrangeActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class MyMapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener{

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            float density = BaseApplication.getDensity();
            int x = BaseApplication.getScreenWidth()/2;
            int y = (int)(222*density);
            android.graphics.Point point = new android.graphics.Point(x,y);
            LatLng center = locationMap.getProjection().fromScreenLocation(point);

            locationLa =center.latitude ;
            locationLo = center.longitude;

            Log.d("纬度",center.latitude+"");
            Log.d("经度",center.longitude+"");

            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(center));

            poiSearch.searchNearby(new PoiNearbySearchOption().keyword("篮球场").location(center).radius(2000));

        }
    }

    private class GeoListener implements OnGetGeoCoderResultListener{

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            addressInfo = reverseGeoCodeResult.getPoiList().get(0).address+ "  "+ reverseGeoCodeResult.getAddressDetail().city + "  "+
                    reverseGeoCodeResult.getAddressDetail().district +"  "+ reverseGeoCodeResult.getAddressDetail().street +
                    reverseGeoCodeResult.getAddressDetail().streetNumber;
            Log.d("addressInfo",addressInfo);
        }
    }

    private class PoiListener implements OnGetPoiSearchResultListener{

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            locationMap.clear();
            List<PoiInfo> rs = poiResult.getAllPoi();
            if(rs !=null){

                for(PoiInfo i:rs){
                    Log.d("address",i.address);
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
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        baiduMap.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baiduMap.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baiduMap.onDestroy();
        if (arrangeClient != null)
            arrangeClient.stop();
    }

}
