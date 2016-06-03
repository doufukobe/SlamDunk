package com.fpd.slamdunk.bussiness.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by t450s on 2016/6/2.
 */
public class HomeAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    public HomeAdapter(FragmentActivity fm,List<Fragment> fragmentList){
        super(fm.getSupportFragmentManager());
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
