package com.fpd.slamdunk.bussiness.myactdetial;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.basecore.util.MyScrollView;
import com.fpd.core.deleteact.DeleteAction;
import com.fpd.core.myactdetail.MyActDetailAction;
import com.fpd.model.acthead.MyActDetailEntity;
import com.fpd.model.acthead.MyActHeadEntity;
import com.fpd.model.success.SuccessEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.myactdetial.adapter.HeadAdapter;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by t450s on 2016/6/15.
 */
public class MyActDetailActivity extends CommenActivity {

    private RecyclerView headRecycleView;
    private List<MyActHeadEntity> mDatas;
    private MyScrollView scrollView;
    private TextView actName;
    private TextView actAddress;
    private TextView actMembers;
    private TextView actTime;
    private TextView actIntroduce;
    private ButtonRectangle cancelBtn;
    private TextView topTitle;
    private Button  backBtn;
    private String actId;
    private TextView actIntroduceTip;
    private MyActDetailAction detailAction;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ImageView img;
    private int[] img_id = {R.mipmap.act_detail01,R.mipmap.act_detail02,R.mipmap.act_detail03,R.mipmap.act_detail04,R.mipmap.act_detail05};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_act_detail);
        actId = getIntent().getStringExtra("ACTID");
        detailAction = new MyActDetailAction(this);
        initView();
        setClick();
        getDetail();
    }

    private void initView() {
        scrollView= (MyScrollView) findViewById(R.id.my_act_scroll);
        headRecycleView = (RecyclerView) findViewById(R.id.my_act_detail_heads);
        actName = (TextView) findViewById(R.id.my_act_detail_name);
        actAddress = (TextView) findViewById(R.id.my_act_detail_address);
        actMembers = (TextView) findViewById(R.id.my_act_detail_member);
        actTime = (TextView) findViewById(R.id.my_act_detail_time);
        backBtn = (Button) findViewById(R.id.back_button);
        topTitle = (TextView) findViewById(R.id.top_title);
        topTitle.setText("活动详情");
        actIntroduce = (TextView) findViewById(R.id.my_act_detail_introduce);
        cancelBtn = (ButtonRectangle) findViewById(R.id.my_act_detail_cancel);
        actIntroduceTip = (TextView) findViewById(R.id.my_act_detail_tip);
        img = (ImageView) findViewById(R.id.my_act_detail_img);
        Random random = new Random();
        img.setImageResource(img_id[random.nextInt(4)+1]);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        headRecycleView.setLayoutManager(lm);
    }

    private void getDetail(){
        detailAction.getDetail(actId, new CallBackListener<MyActDetailEntity>() {
            @Override
            public void onSuccess(MyActDetailEntity result) {
                fullView(result);
            }

            @Override
            public void onFailure(String Message) {

            }
        });
    }

    private void setClick(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDDialog dialog = new SDDialog(MyActDetailActivity.this, "确认退出活动吗？", new SDDialog.Callback() {
                    @Override
                    public void sureCallBack() {
                        DeleteAction deleteAction = new DeleteAction(MyActDetailActivity.this);
                        deleteAction.deleteAct(Config.userId, actId+"", new CallBackListener<SuccessEntity>() {
                            @Override
                            public void onSuccess(SuccessEntity result) {

                            }

                            @Override
                            public void onFailure(String Message) {

                            }
                        });
                    }

                    @Override
                    public void cancelCallBack() {

                    }
                });
                dialog.show();
            }
        });

    }


    private void fullView(MyActDetailEntity result){
        actName.setText(result.getActName());
        actTime.setText(getDateToString(result.getActTime()));
        actAddress.setText(result.getAddressInfo());
        actMembers.setText(result.getCurPeopleNum()+"");
        if (result.getActInfo() !=null && !result.getActInfo().isEmpty()){
            actIntroduce.setText(result.getActInfo());
        }else{
            actIntroduceTip.setVisibility(View.GONE);
        }
        HeadAdapter hd = new HeadAdapter(this,result.getActMembers());
        headRecycleView.setAdapter(hd);
    }

    private String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

}