package com.fpd.slamdunk.bussiness.home.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fpd.basecore.util.CircleImage;
import com.fpd.basecore.util.DensityUtil;
import com.fpd.basecore.util.PersonalView;
import com.fpd.slamdunk.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by solo on 2016/6/2.
 */
public class MyFragment extends Fragment implements PersonalView.OnScrollListener
{

    private Context mContext;
    private View mContentView;

    private PersonalView mSrollView;

    private View mTopbar;
    private View mTopLayout;
    private View mTopLayout1;
    private ImageView mBackground;
    private ImageView mBackground1;
    private CircleImage mIcon;

    private int iconScrollLegth;
    private int topLayoutScrollLength;
    private int iconMinSize;
    private int iconMaxSize;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContentView=inflater.inflate(R.layout.fragment_my,container,false);
        mContext=getActivity();
        initViews();
        return mContentView;
    }

    private void initViews()
    {
        mSrollView=(PersonalView)mContentView.findViewById(R.id.id_my_scrollview);

        mTopbar=mContentView.findViewById(R.id.id_my_top_bar);

        mTopLayout=mContentView.findViewById(R.id.id_my_top_ly);
        mTopLayout1=mContentView.findViewById(R.id.id_my_top_ly_1);

        mBackground=(ImageView)mContentView.findViewById(R.id.id_my_top_bg);
        mBackground1=(ImageView)mContentView.findViewById(R.id.id_my_top_bg_1);

        mIcon=(CircleImage)mContentView.findViewById(R.id.id_my_icon);

        initCircleImag(mIcon);
        initConstants();
        initEvents();
    }

    private void initCircleImag(CircleImage view)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.mipmap.x_3);
        view.setBitmap(bitmap);
    }

    private void initEvents()
    {
        mSrollView.setOnScrollListener(this);
        mTopLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i("TAG", "mTopBackground.onClick()");
            }
        });

        mIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i("TAG", "mIcon.onClick()");
            }
        });
    }

    private void initDatas()
    {

    }

    private void initConstants()
    {
        topLayoutScrollLength=DensityUtil.dip2px(mContext,160);

        iconScrollLegth= DensityUtil.dip2px(mContext,125);
        iconMinSize=DensityUtil.dip2px(mContext,50);
        iconMaxSize=DensityUtil.dip2px(mContext,70);
    }

    //t:0~maxScrollLength
    @Override
    public void onScroll(int t, int maxScrollLength)
    {
        float factor=(float)t/(maxScrollLength);
        factor=(factor>1) ? 1 : factor;
        if(t==0)
        {
            mTopbar.setVisibility(View.GONE);

            mTopLayout1.setVisibility(View.GONE);
            mTopLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mTopbar.setVisibility(View.VISIBLE);

            mTopLayout1.setVisibility(View.VISIBLE);
            mTopLayout.setVisibility(View.GONE);
        }

        ViewHelper.setAlpha(mTopbar, factor);

        ViewHelper.setTranslationY(mTopLayout1,-topLayoutScrollLength*factor);

        ViewHelper.setPivotX(mIcon, 0);
        ViewHelper.setPivotY(mIcon, mIcon.getHeight() / 2);
        ViewHelper.setScaleY(mIcon, 1 - (1 - ((float) iconMinSize / iconMaxSize)) * factor);
        ViewHelper.setScaleX(mIcon, 1 - (1 - ((float) iconMinSize / iconMaxSize)) * factor);
        ViewHelper.setTranslationY(mIcon, -iconScrollLegth * factor);
    }
}
