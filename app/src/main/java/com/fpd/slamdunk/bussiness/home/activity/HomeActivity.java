package com.fpd.slamdunk.bussiness.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.adapter.HomeAdapter;
import com.fpd.slamdunk.bussiness.home.fragment.InviteFragment;
import com.fpd.slamdunk.bussiness.home.fragment.MyFragment;
import com.fpd.slamdunk.bussiness.home.fragment.ShareFragment;

import java.util.ArrayList;
import java.util.List;

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
    }

    private void initView() {
        backButton = (Button) findViewById(R.id.back_button);
        topTitle = (TextView) findViewById(R.id.top_title);
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
                setBackground(2);
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
                break;
            case 1:
                homeViewPager.setCurrentItem(1);
                sharePage.setSelected(true);
                topTitle.setText("评球");
                break;
            case 2:
                homeViewPager.setCurrentItem(2);
                myPage.setSelected(true);
                topTitle.setText("账户");
                break;
        }

    }
}
