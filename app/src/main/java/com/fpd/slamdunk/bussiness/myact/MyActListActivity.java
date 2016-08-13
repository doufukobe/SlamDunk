package com.fpd.slamdunk.bussiness.myact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fpd.model.userinfo.HostedEntity;
import com.fpd.model.userinfo.UserInfoEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
import com.fpd.slamdunk.bussiness.myact.adapter.MyActListAdapter;

import java.util.ArrayList;

/**
 * Created by t450s on 2016/6/10.
 */
public class MyActListActivity extends CommenActivity {

    private ListView myActList;
    private Button backBtn;
    private TextView title;
    private MyActListAdapter mAdapter;
    private UserInfoEntity userInfo;
    private ArrayList<HostedEntity> myacts;
    private TextView noacttip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_act_list);
        userInfo = (UserInfoEntity) getIntent().getSerializableExtra("userInfo");
        myacts = (ArrayList<HostedEntity>) userInfo.getUserHostedAct();
        initView();
        setClick();
    }

    private void initView() {
        myActList = (ListView) findViewById(R.id.my_act_list);
        backBtn = (Button) findViewById(R.id.back_button);
        title = (TextView) findViewById(R.id.top_title);
        title.setText("组织的活动");
        noacttip=(TextView)findViewById(R.id.id_myactlist_tip);

        if (myacts !=null && myacts.size()!=0)
        {
            mAdapter = new MyActListAdapter(this,myacts);
            myActList.setAdapter(mAdapter);
            noacttip.setVisibility(View.INVISIBLE);

        }
        else
        {
            noacttip.setText("您还没有创建任何活动");
            noacttip.setVisibility(View.VISIBLE);
        }

    }


    private void setClick(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }



}
