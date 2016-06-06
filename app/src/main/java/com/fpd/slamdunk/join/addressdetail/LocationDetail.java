package com.fpd.slamdunk.join.addressdetail;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.widget.WalkingRouteOverlay;

/**
 * Created by t450s on 2016/6/6.
 */
public class LocationDetail extends CommenActivity implements BDLocationListener,OnGetRoutePlanResultListener {

    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private RoutePlanSearch routePlanSearch;
    private double latitude;
    private double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail_layout);
        mapView = (MapView) findViewById(R.id.location_detail);
        baiduMap = mapView.getMap();
        locationClient = new LocationClient(this);
        initLocation();
        locationClient.registerLocationListener(this);
        locationClient.start();
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        final LatLng cenpt =  new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
        latitude = getIntent().getDoubleExtra("LATITUDE",bdLocation.getLatitude());
        longitude = getIntent().getDoubleExtra("LONGITUDE",bdLocation.getLongitude());
        LatLng end = new LatLng(latitude,longitude);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(16)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
        WalkingRoutePlanOption option = new WalkingRoutePlanOption();
        option.from(PlanNode.withLocation(cenpt));
        option.to(PlanNode.withLocation(end));
        routePlanSearch.walkingSearch(option);
        locationClient.stop();
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
        locationClient.setLocOption(option);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        WalkingRouteOverlay walkingRouteOverlay = new WalkingRouteOverlay(baiduMap);
        walkingRouteOverlay.setData(walkingRouteResult.getRouteLines().get(0));
        walkingRouteOverlay.addToMap();
        walkingRouteOverlay.zoomToSpan();
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
}
