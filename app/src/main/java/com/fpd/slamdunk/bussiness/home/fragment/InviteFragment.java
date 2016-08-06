package com.fpd.slamdunk.bussiness.home.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.NetWorkUtil;
import com.fpd.core.invitelist.InviteListAction;
import com.fpd.model.invite.InviteListEntity;
import com.fpd.slamdunk.DBHelper.SDDBHelper;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.adapter.InviteAdapter;
import com.fpd.slamdunk.bussiness.home.adapter.NewInviteAdapter;
import com.fpd.slamdunk.bussiness.home.widget.MyLoadMoreView;
import com.fpd.slamdunk.bussiness.register.activity.RegisterActivity;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by t450s on 2016/6/2.
 */
public class InviteFragment extends Fragment {

    private Context mContext;
    private PullToRefreshRecyclerView listView;
    private LocationClient mLocationClient;

    private List<InviteListEntity> inviteList = new ArrayList<>();
    private NewInviteAdapter mAdapter;
    private InviteListAction inviteListAction;

    private PtrFrameLayout ptrFrameLayout;
    private ListView list;
    private SQLiteDatabase mDates;

    private TextView temptext;
    private RelativeLayout tempView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= getActivity();
        inviteListAction = new InviteListAction(mContext);
        mLocationClient = new LocationClient(mContext);
        initLocation();
        mLocationClient.registerLocationListener(new InviteLocation());
        mDates =  new SDDBHelper(mContext).getWritableDatabase();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (ptrFrameLayout !=null){
               ptrFrameLayout.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       ptrFrameLayout.autoRefresh(true);
                   }
               },200);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.new_invite_fragment,null);
        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.store_house_ptr_frame);
        list = (ListView) view.findViewById(R.id.invite_list);
        tempView = (RelativeLayout) view.findViewById(R.id.tempview);
        temptext = (TextView) view.findViewById(R.id.temp_refresh);
        //listView = (PullToRefreshRecyclerView) view.findViewById(R.id.invite_list);
        initPullList();
        if (getUserVisibleHint())
                getInviteList();
        return view;
    }

    private void getInviteList(){
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            mLocationClient.start();
        }else{
            getDateFromLocal();
        }

    }

    private void initPullList(){
//        listView.setSwipeEnable(true);
//        MyLoadMoreView loadMoreView = new MyLoadMoreView(mContext,listView.getRecyclerView());
//        loadMoreView.setLoadMorePadding(100);
//        listView.setLayoutManager(new LinearLayoutManager(mContext));
//        listView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.i("TAG", "onRefresh");
//                getInviteList();
//            }
//        });
//        mAdapter = new InviteAdapter(mContext,inviteList);
//        listView.setAdapter(mAdapter);
//        listView.onFinishLoading(false, false);

        MaterialHeader materialHeader = new MaterialHeader(mContext);
        int[] colors = {0xffff9800,0xff3F51B5};
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 45, 0, 30);
        materialHeader.setPtrFrameLayout(ptrFrameLayout);

        ptrFrameLayout.setHeaderView(materialHeader);
        ptrFrameLayout.setPinContent(true);
        ptrFrameLayout.addPtrUIHandler(materialHeader);
        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setDurationToCloseHeader(1500);


        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getInviteList();
            }
        });
        mAdapter = new NewInviteAdapter(mContext,inviteList);
        list.setAdapter(mAdapter);
        temptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInviteList();
            }
        });
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

    private void insertLocalDB(List<InviteListEntity> result){
        if (mDates == null) {
            mDates =  new SDDBHelper(mContext).getWritableDatabase();
        }
        mDates.execSQL("DELETE FROM "+ SDDBHelper.TABLE_SLAMDUNK);
        if (result == null){
            return;
        }
        for (InviteListEntity listEntity : result) {
            ContentValues values = new ContentValues();
            values.put("actid" , listEntity.getActId());
            values.put("actname", listEntity.getActName());
            values.put("actimg" , listEntity.getActImg());
            values.put("actoriginatorname", listEntity.getActOriginatorName());
            values.put("acttime", (float)listEntity.getActTime());
            values.put("curpeoplenum", listEntity.getCurPeopleNum());
            values.put("maxpeoplenum", listEntity.getMaxPeopleNum());
            values.put("addressdist", listEntity.getAddressDist());
            mDates.insert(SDDBHelper.TABLE_SLAMDUNK,null,values);
        }
        Log.d("TAG","insertLocal success");
    }

    private void getDateFromLocal(){
        if (mDates == null) {
            mDates =  new SDDBHelper(mContext).getWritableDatabase();
        }
        Cursor cursor = mDates.query(SDDBHelper.TABLE_SLAMDUNK,null,null,null,null,null,null,null);
        List<InviteListEntity> result = new ArrayList<InviteListEntity>();
        while(cursor.moveToNext()) {
            InviteListEntity temp  = new InviteListEntity();
            temp.setActId(cursor.getInt(cursor.getColumnIndex("actid")));
            temp.setActImg(cursor.getString(cursor.getColumnIndex("actimg")));
            temp.setActName(cursor.getString(cursor.getColumnIndex("actname")));
            temp.setActOriginatorName(cursor.getString(cursor.getColumnIndex("actoriginatorname")));
            temp.setActTime((long) cursor.getFloat(cursor.getColumnIndex("acttime")));
            temp.setCurPeopleNum(cursor.getInt(cursor.getColumnIndex("curpeoplenum")));
            temp.setMaxPeopleNum(cursor.getInt(cursor.getColumnIndex("maxpeoplenum")));
            temp.setAddressDist(cursor.getString(cursor.getColumnIndex("addressdist")));
            result.add(temp);
        }
        if (!result.isEmpty()) {
            tempView.setVisibility(View.GONE);
            ptrFrameLayout.setVisibility(View.VISIBLE);
            mAdapter.fillView(result);
            Log.d("TAG", "readFromLocal success");
        }else {
            tempView.setVisibility(View.VISIBLE);
            ptrFrameLayout.setVisibility(View.GONE);
        }
    }

    private class InviteLocation implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
                    bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                Log.d("latitude",bdLocation.getLatitude()+"");
                Log.d("longitude", bdLocation.getLongitude() + "");
                if (NetWorkUtil.isNetworkAvailable(mContext)) {
                    inviteListAction.getInviteList(bdLocation.getLatitude(), bdLocation.getLongitude(), new CallBackListener<List<InviteListEntity>>() {
                        @Override
                        public void onSuccess(List<InviteListEntity> result) {
                            mAdapter.fillView(result);
                            ptrFrameLayout.refreshComplete();
                            new Thread(new InsertDates(result)).start();
                            if (result.isEmpty()) {
                                tempView.setVisibility(View.VISIBLE);
                                ptrFrameLayout.setVisibility(View.GONE);
                            }else{
                                tempView.setVisibility(View.GONE);
                                ptrFrameLayout.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(String Message) {
                            ptrFrameLayout.refreshComplete();
                            Toast.makeText(mContext,Message,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    getDateFromLocal();
                }

            }else{
                getDateFromLocal();
                listView.setOnRefreshComplete();
                Toast.makeText(mContext,"定位失败,无法为您提供活动信息",Toast.LENGTH_SHORT).show();
            }
            mLocationClient.stop();
        }
    }

    private class InsertDates implements Runnable{

        private List<InviteListEntity> result;

        InsertDates(List<InviteListEntity> result){
            this.result = result;
        }

        @Override
        public void run() {
            insertLocalDB(result);
        }
    }
}
