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

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.util.CircleImage;
import com.fpd.basecore.util.ColorIcon;
import com.fpd.core.userinfo.UserInfoAction;
import com.fpd.model.userinfo.UserInfoEntity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.login.activity.LoginActivity;
import com.fpd.slamdunk.bussiness.myact.MyActListActivity;
import com.fpd.slamdunk.myjoinact.MyJoinActActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

    private UserInfoAction userAction;
    private UserInfoEntity userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContentView=inflater.inflate(R.layout.fragment_my, container, false);
        mContext=getActivity();
        initViews();
        getUserInfo();
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
                if (userInfo !=null){
                Intent intent = new Intent(getActivity(), MyActListActivity.class);
                intent.putExtra("userInfo",userInfo);
                startActivity(intent);}
                break;
            case R.id.id_my_ly_join:
                if (userInfo !=null){
                Intent intent1 = new Intent(getActivity(), MyJoinActActivity.class);
                intent1.putExtra("userInfo", userInfo);
                startActivity(intent1);}
                break;
            case R.id.id_my_ly_3:

                break;
            case R.id.id_my_ly_4:
                Config.userId = "";
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent2);
                getActivity().finish();
                break;
        }
    }

    private void getUserInfo(){
        userAction = new UserInfoAction(getActivity());
        userAction.GetUserInfo(Config.userId, new CallBackListener<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity result) {
                userInfo = result;
                fullView();
            }

            @Override
            public void onFailure(String Message) {

            }
        });
    }

    private void fullView() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.invite_photo)
                .showImageForEmptyUri(R.mipmap.default_ball)
                .showImageOnFail(R.mipmap.invite_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();

        ImageLoader.getInstance().loadImage(userInfo.getUserHeadUrl(), options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mIcon.setBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        mName.setText(userInfo.getUserPetName());
        String[] site = userInfo.getUserPosition().split(":");
        if (site.length == 1){
            mSiteOne.setText(site[0]);
        }else{
            mSiteOne.setText(site[0]);
            mSizteTwo.setText(site[1]);
        }
        mSexAge.setText(userInfo.getUserSex());
        mZanAmount.setText(userInfo.getUserLiked()+"");
        mAccount.setText(Config.userId);
    }
}
