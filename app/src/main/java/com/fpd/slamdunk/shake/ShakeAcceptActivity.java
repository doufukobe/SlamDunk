package com.fpd.slamdunk.shake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.core.actdetail.ActDetail;
import com.fpd.core.shake.ShakeAction;
import com.fpd.model.actdetial.ActivityDetailEntitiy;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.join.addressdetail.LocationDetail;
import com.gc.materialdesign.views.ButtonRectangle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by solo on 2016/6/25.
 */
public class ShakeAcceptActivity extends CommenActivity implements View.OnClickListener
{

    private TextView mAddress;
    private TextView mTime;
    private ButtonRectangle mAccept;
    private ButtonRectangle mRefuse;
    private View mLyAddress;
    private View mBack;

    private String actId;
    private ActDetail actDetail;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private double latitude;
    private double longitude;

    private ShakeAction shakeAction;
    private String[] tip={"拒绝成功","接受成功"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_accept);
        actId=getIntent().getStringExtra("ACTID");
        actDetail=new ActDetail(this);
        shakeAction=new ShakeAction(this);

        getActDetail();
        initViews();
        initEvents();
    }

    private void initViews()
    {
        mAddress=(TextView)findViewById(R.id.id_shake_accept_address_tv);
        mTime=(TextView)findViewById(R.id.id_shake_accept_time_tv);
        mAccept=(ButtonRectangle)findViewById(R.id.id_shake_accept_bt);
        mRefuse=(ButtonRectangle)findViewById(R.id.id_shake_refuse_bt);
        mLyAddress=findViewById(R.id.id_shake_accept_detail_address);
        mBack=findViewById(R.id.id_shake_accept_back_ly);
    }

    private void initEvents()
    {
        mAccept.setOnClickListener(this);
        mRefuse.setOnClickListener(this);
        mLyAddress.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void fullView(ActivityDetailEntitiy result)
    {
        if(result==null) return;
        String address = result.getAddressInfo();
        if (address.indexOf(" ") >0){
            address = address.substring(0,address.indexOf(" "));}
        mAddress.setText(address);
        mTime.setText(getDateToString(result.getActTime() * 1000));
        longitude = result.getAddressLongitude();
        latitude = result.getAddressLatitude();
    }

    private String getDateToString(long time)
    {
        Date d = new Date(time);
        return sf.format(d);
    }

    private void getActDetail()
    {
        actDetail.getActivityDetail(actId, new CallBackListener<ActivityDetailEntitiy>()
        {
            @Override
            public void onSuccess(ActivityDetailEntitiy result)
            {
                fullView(result);
            }

            @Override
            public void onFailure(String Message)
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_shake_accept_back_ly:
                finish();
                break;
            case R.id.id_shake_accept_detail_address:
                Intent intent = new Intent(this, LocationDetail.class);
                intent.putExtra("LATITUDE", latitude);
                intent.putExtra("LONGITUDE", longitude);
                startActivity(intent);
                break;
            case R.id.id_shake_accept_bt:
                handleAct(1);
                break;
            case R.id.id_shake_refuse_bt:
                handleAct(-1);
                break;
        }

    }

    private void handleAct(final int isAccept)
    {
        shakeAction.quickAccept(actId, Config.userId, isAccept, new CallBackListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                SDDialog sdDialog = new SDDialog(ShakeAcceptActivity.this, tip[(isAccept+1)/2]
                        ,new dialogCallback());
                sdDialog.show();
            }

            @Override
            public void onFailure(String Message)
            {

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
