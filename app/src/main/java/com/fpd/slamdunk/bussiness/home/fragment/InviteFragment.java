package com.fpd.slamdunk.bussiness.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fpd.api.callback.CallBackListener;
import com.fpd.core.invitelist.InviteListAction;
import com.fpd.model.invite.InviteListEntity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.adapter.InviteAdapter;
import com.fpd.slamdunk.bussiness.home.widget.MyLoadMoreView;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t450s on 2016/6/2.
 */
public class InviteFragment extends Fragment {

    private Context mContext;
    private PullToRefreshRecyclerView listView;
    private LocationClient mLocationClient;

    private List<InviteListEntity> inviteList = new ArrayList<>();
    private InviteAdapter mAdapter;
    private InviteListAction inviteListAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= getActivity();
        inviteListAction = new InviteListAction(mContext);
        mLocationClient = new LocationClient(mContext);
        initLocation();
        mLocationClient.registerLocationListener(new InviteLocation());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (listView !=null){
                getInviteList();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.invite_fragment,null);
        listView = (PullToRefreshRecyclerView) view.findViewById(R.id.invite_list);
        initPullList();
        if (getUserVisibleHint())
                getInviteList();
        return view;
    }

    private void getInviteList(){
        mLocationClient.start();
    }

    private void initPullList(){
        listView.setSwipeEnable(true);
        MyLoadMoreView loadMoreView = new MyLoadMoreView(mContext,listView.getRecyclerView());
        loadMoreView.setLoadMorePadding(100);
        listView.setLayoutManager(new LinearLayoutManager(mContext));
        listView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG", "onRefresh");
                getInviteList();
            }
        });
        mAdapter = new InviteAdapter(mContext,inviteList);
        listView.setAdapter(mAdapter);
        listView.onFinishLoading(false, false);
    }

    private void  initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    private class InviteLocation implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
                    bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                Log.d("latitude",bdLocation.getLatitude()+"");
                Log.d("longitude",bdLocation.getLongitude()+"");
                inviteListAction.getInviteList(bdLocation.getLatitude(), bdLocation.getLongitude(), new CallBackListener<List<InviteListEntity>>() {
                    @Override
                    public void onSuccess(List<InviteListEntity> result) {
                        mAdapter.fillView(result);
                        listView.setOnRefreshComplete();
                    }

                    @Override
                    public void onFailure(String Message) {
                        listView.setOnRefreshComplete();
                        Toast.makeText(mContext,Message,Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                listView.setOnRefreshComplete();
                Toast.makeText(mContext,"定位失败,无法为您提供活动信息",Toast.LENGTH_SHORT).show();
            }
            mLocationClient.stop();
        }
    }
}
