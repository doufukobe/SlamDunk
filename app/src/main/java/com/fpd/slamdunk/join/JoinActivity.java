package com.fpd.slamdunk.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpd.api.callback.CallBackListener;
import com.fpd.core.actdetail.ActDetail;
import com.fpd.model.actdetial.ActivityDetailEntitiy;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.join.addressdetail.LocationDetail;
import com.fpd.slamdunk.join.submit.JoinSubmitActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by t450s on 2016/6/5.
 */
public class JoinActivity extends CommenActivity {

    private TextView actName;
    private TextView actTime;
    private TextView memberList;
    private TextView addressDist;
    private TextView addressInfo;
    private TextView memberInfo;
    private TextView actIntroduce;
    private TextView ballInfo;

    private ImageView topImage;
    private TextView joinAct;
    private RelativeLayout addresslayout;
    private String actId;

    private ActDetail actDetail;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private double addressLongitude;
    private double addressLatitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);
        initView();
        actDetail = new ActDetail(this);
        actId = getIntent().getStringExtra("ACTID");
        getActivityDetail();
        setClick();
    }

    private void initView() {
        actName = (TextView) findViewById(R.id.join_actname);
        addressDist = (TextView) findViewById(R.id.join_distance);
        memberList = (TextView) findViewById(R.id.join_member);
        joinAct = (TextView) findViewById(R.id.join_act);
        addressInfo = (TextView) findViewById(R.id.join_address);
        actTime = (TextView) findViewById(R.id.join_time);
        memberInfo = (TextView) findViewById(R.id.join_memberinfo);
        topImage = (ImageView) findViewById(R.id.join_top_img);
        actIntroduce = (TextView) findViewById(R.id.act_introduce);
        addresslayout = (RelativeLayout) findViewById(R.id.address_layout);
        ballInfo = (TextView) findViewById(R.id.join_hasball);
    }

    private void setClick(){
        joinAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, JoinSubmitActivity.class);
                intent.putExtra("ACTID","8");
                startActivity(intent);
            }
        });

        addresslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, LocationDetail.class);
                intent.putExtra("LATITUDE",addressLatitude);
                intent.putExtra("LONGITUDE",addressLongitude);
                startActivity(intent);
            }
        });
    }

    private void getActivityDetail(){
        actDetail.getActivityDetail(actId, new CallBackListener<ActivityDetailEntitiy>() {
            @Override
            public void onSuccess(ActivityDetailEntitiy result) {
                fullView(result);
            }

            @Override
            public void onFailure(String Message) {

            }
        });
    }

    private void fullView(ActivityDetailEntitiy result) {
        actName.setText(result.getActName());
        addressDist.setText(result.getAddressDist());
        String originator = result.getActOriginator();
        String member = result.getMemberList().get(0).getUserName();
        member += result.getMemberList().get(1).getUserName();
        memberList.setText(originator+","+member);
        addressInfo.setText(result.getAddressInfo());
        actTime.setText(getDateToString(result.getActTime()*1000));
        memberInfo.setText("当前人数"+result.getCurPeopleNum()+" 目标人数"+result.getMaxPeopleNum());
        String hasball = null;
        if (result.isHasEquipment())
            hasball = "有球";
        else
            hasball = "无球";
        ballInfo.setText(hasball);
        actIntroduce.setText(result.getActInfo());
        addressLongitude = result.getAddressLongitude();
        addressLatitude = result.getAddressLatitude();
    }

    private String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
