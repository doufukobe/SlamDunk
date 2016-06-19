package com.fpd.slamdunk.join;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.core.actdetail.ActDetail;
import com.fpd.model.actdetial.ActivityDetailEntitiy;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.login.activity.LoginActivity;
import com.fpd.slamdunk.join.addressdetail.LocationDetail;
import com.fpd.slamdunk.join.submit.JoinSubmitActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    private String act_photo;

    private DisplayImageOptions options;
    private Button backButton;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);
        act_photo = getIntent().getStringExtra("ACTPHOTOURL");
        actId = getIntent().getStringExtra("ACTID");
        initView();
        actDetail = new ActDetail(this);
        getActivityDetail();
        setClick();
        initOption();
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
        backButton = (Button) findViewById(R.id.back_button);
        titleView = (TextView) findViewById(R.id.top_title);
        titleView.setText("活动详情");
    }

    private void setClick(){
        joinAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Config.userId.isEmpty()){
                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(JoinActivity.this, JoinSubmitActivity.class);
                    intent.putExtra("ACTID",actId);
                    startActivity(intent);
                }

            }
        });

        addresslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, LocationDetail.class);
                intent.putExtra("LATITUDE", addressLatitude);
                intent.putExtra("LONGITUDE", addressLongitude);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        addressDist.setText(getIntent().getStringExtra("DISTANCE"));
        String originator = result.getActOriginatorName();
        String member = "";
        for (int i=0;i<result.getMemberList().size();i++){
            member += result.getMemberList().get(i).getUserName()+",";
        }
        if (member.length()>0){
            member = member.substring(0,member.length()-1);
        }
        if (member.contains(originator))
            joinAct.setVisibility(View.GONE);
        memberList.setText(member);
        addressInfo.setText(result.getAddressInfo().substring(0,result.getAddressInfo().indexOf(" ")));
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
        if (act_photo !=null && !act_photo.isEmpty()){
            ImageLoader.getInstance().displayImage(act_photo, topImage, options);
        }

    }

    private String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    private void initOption(){

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.invite_photo)
                .showImageForEmptyUri(R.mipmap.default_ball)
                .showImageOnFail(R.mipmap.invite_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
    }
}
