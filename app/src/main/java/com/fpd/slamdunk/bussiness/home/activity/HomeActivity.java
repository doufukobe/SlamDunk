package com.fpd.slamdunk.bussiness.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fpd.basecore.config.Config;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.application.CommenApplication;
import com.fpd.slamdunk.arrange.ArrangeActivity;
import com.fpd.slamdunk.bussiness.home.adapter.HomeAdapter;
import com.fpd.slamdunk.bussiness.home.fragment.InviteFragment;
import com.fpd.slamdunk.bussiness.home.fragment.MyFragment;
import com.fpd.slamdunk.bussiness.home.fragment.ShareFragment;
import com.fpd.slamdunk.bussiness.login.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by t450s on 2016/6/2.
 */
public class HomeActivity extends CommenActivity {


    private Button backButton;
    private TextView topTitle;
    private ViewPager homeViewPager;

    private Button invitePage;
    private Button sharePage;
    private Button myPage;
    private Button action_button;

    private long currTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initView();
        fillView();
        setClick();
    }

    private void fillView() {
        backButton.setVisibility(View.INVISIBLE);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new InviteFragment());
        fragmentList.add(new ShareFragment());
        fragmentList.add(new MyFragment());
        homeViewPager.setAdapter(new HomeAdapter(this, fragmentList));
        setBackground(0);
        Log.d("registerId", JPushInterface.getRegistrationID(this));
    }

    private void initView() {
        backButton = (Button) findViewById(R.id.back_button);
        topTitle = (TextView) findViewById(R.id.top_title);
        action_button = (Button) findViewById(R.id.top_action);
        homeViewPager = (ViewPager) findViewById(R.id.home_viewPager);

        invitePage = (Button) findViewById(R.id.pager1);
        sharePage = (Button) findViewById(R.id.pager2);
        myPage = (Button) findViewById(R.id.pager3);
    }

    private void setClick() {
        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    setBackground(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        invitePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(0);
            }
        });

        sharePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(1);
            }
        });

        myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.userId.isEmpty()){
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.putExtra("USERINFO","userInfo");
                    startActivity(intent);
                }else{
                    setBackground(2);
                }
            }
        });

        action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ArrangeActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setBackground(int index){
        invitePage.setSelected(false);
        sharePage.setSelected(false);
        myPage.setSelected(false);

        topTitle.setText("");
        switch (index){
            case 0:
                homeViewPager.setCurrentItem(0);
                invitePage.setSelected(true);
                topTitle.setText("约球");
                action_button.setVisibility(View.VISIBLE);
                action_button.setText("发起活动");
                break;
            case 1:
                homeViewPager.setCurrentItem(1);
                sharePage.setSelected(true);
                topTitle.setText("评球");
                action_button.setVisibility(View.INVISIBLE);
                break;
            case 2:
                homeViewPager.setCurrentItem(2);
                myPage.setSelected(true);
                topTitle.setText("账户");
                action_button.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (System.currentTimeMillis() - currTime >2000){
            Toast.makeText(this,"再按一次，退出程序",Toast.LENGTH_SHORT).show();
            currTime = System.currentTimeMillis();
        }else{
            ((CommenApplication)getApplication()).exit(false);
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int page = intent.getIntExtra("selectPage",0);
        setBackground(page);
    }
}
