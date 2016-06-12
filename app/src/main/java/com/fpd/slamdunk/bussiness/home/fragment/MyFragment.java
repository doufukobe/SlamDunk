package com.fpd.slamdunk.bussiness.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpd.basecore.util.CircleImage;
import com.fpd.basecore.util.ColorIcon;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.setting.SettingActivity;

/**
 * Created by solo on 2016/6/2.
 */
public class MyFragment extends Fragment implements View.OnClickListener
{

    private Context mContext;
    private View mContentView;

    private CircleImage mIcon;
    private TextView mName;
    private TextView mSiteOne;
    private TextView mSizteTwo;
    private TextView mSexAge;
    private ColorIcon mZanIcon;
    private TextView mZanAmount;
    private TextView mAccount;

    private View mCreate;
    private View mJoin;
    private View mSet;
    private View mQuit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContentView=inflater.inflate(R.layout.fragment_my, container, false);
        mContext=getActivity();
        initViews();
        return mContentView;
    }

    private void initViews()
    {
        mIcon=(CircleImage)mContentView.findViewById(R.id.id_my_icon);
        mName=(TextView)mContentView.findViewById(R.id.id_my_name);
        mSiteOne=(TextView)mContentView.findViewById(R.id.id_my_site_1);
        mSizteTwo=(TextView)mContentView.findViewById(R.id.id_my_site_2);
        mSexAge=(TextView)mContentView.findViewById(R.id.id_my_sex_age);
        mZanIcon=(ColorIcon)mContentView.findViewById(R.id.id_my_zan_icon);
        mZanAmount=(TextView)mContentView.findViewById(R.id.id_my_zan_amount);
        mAccount=(TextView)mContentView.findViewById(R.id.id_my_account_number);

        mCreate=mContentView.findViewById(R.id.id_my_ly_create);
        mJoin=mContentView.findViewById(R.id.id_my_ly_join);
        mSet=mContentView.findViewById(R.id.id_my_ly_3);
        mQuit=mContentView.findViewById(R.id.id_my_ly_4);

        initCircleImag(mIcon);
        initColorIcon(mZanIcon);
        initEvents();
    }

    private void initCircleImag(CircleImage view)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.mipmap.x_3);
        view.setBitmap(bitmap);
    }

    private void initColorIcon(ColorIcon view)
    {
        view.setColor(this.getResources().getColor(R.color.gray01));
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.like33);
        view.setBitmap(bitmap);
    }

    private void initEvents()
    {
        mIcon.setOnClickListener(this);
        mCreate.setOnClickListener(this);
        mJoin.setOnClickListener(this);
        mSet.setOnClickListener(this);
        mQuit.setOnClickListener(this);
    }

    private void initDatas()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_my_icon:

                break;
            case R.id.id_my_ly_create:

                break;
            case R.id.id_my_ly_join:

                break;
            case R.id.id_my_ly_3:
                Intent intent=new Intent(mContext, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.id_my_ly_4:

                break;
        }
    }
}
