package com.fpd.slamdunk.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.util.CircleImage;
import com.fpd.basecore.util.DensityUtil;
import com.fpd.core.userinfo.UserInfoAction;
import com.fpd.model.userinfo.UserInfoEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

/**
 * Created by solo on 2016/6/11.
 */
public class SettingActivity extends CommenActivity implements View.OnClickListener
{

    private UserInfoEntity userInfo;

    private CircleImage mIcon;
    private TextView mName;
    private TextView mAccount;
    private TextView mSite;
    private TextView mSex;
    private TextView mAge;

    private View mLyName;
    private View mLySite;
    private View mLySex;
    private View mLyAge;

    private View mLyPopupSexMan;
    private View mLyPopupSexWoman;
    private RadioButton mRbMan;
    private RadioButton mRbWoman;
    private PopupWindow mSexPopup;

    private CheckBox mCbPG;
    private CheckBox mCbSG;
    private CheckBox mCbSF;
    private CheckBox mCbPF;
    private CheckBox mCbCF;
    private PopupWindow mSitePopup;
    private TextView mSiteSave;

    private String nameString;
    private String sexString;
    private String siteString;
    private String ageString;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
//        getUserInfo();
    }

    private void initViews()
    {
        mIcon=(CircleImage)findViewById(R.id.id_setting_iv_icon);
        mName=(TextView)findViewById(R.id.id_setting_tv_name);
        mAccount=(TextView)findViewById(R.id.id_setting_tv_account);
        mSite=(TextView)findViewById(R.id.id_setting_tv_site);
        mSex=(TextView)findViewById(R.id.id_setting_tv_sex);
        mAge=(TextView)findViewById(R.id.id_setting_tv_age);

        mLyName=findViewById(R.id.id_setting_ly_name);
        mLySite=findViewById(R.id.id_setting_ly_site);
        mLySex=findViewById(R.id.id_setting_ly_sex);
        mLyAge=findViewById(R.id.id_setting_ly_age);

        initCircleImage(mIcon);
        initSexPopupWindow();
        initSitePopupWindow();
        initEvents();
    }

    private void initSexPopupWindow()
    {
        View popupView= LayoutInflater.from(this).inflate(R.layout.popup_sex, null);
        mSexPopup=new PopupWindow(popupView, DensityUtil.dip2px(this,250),
                DensityUtil.dip2px(this,180),true);
        mLyPopupSexMan=popupView.findViewById(R.id.id_popup_sex_man);
        mLyPopupSexWoman=popupView.findViewById(R.id.id_popup_sex_woman);
        mRbMan=(RadioButton)popupView.findViewById(R.id.id_popup_sex_rb_man);
        mRbWoman=(RadioButton)popupView.findViewById(R.id.id_popup_sex_rb_woman);
        mSexPopup.setTouchable(true);
        mSexPopup.setOutsideTouchable(true);
        mSexPopup.setFocusable(true);
        mSexPopup.setBackgroundDrawable(new ColorDrawable());
    }

    private void initSitePopupWindow()
    {
        View popupView= LayoutInflater.from(this).inflate(R.layout.popup_site, null);
        mSitePopup=new PopupWindow(popupView, DensityUtil.dip2px(this,250),
                DensityUtil.dip2px(this,300),true);
        mCbPG=(CheckBox)popupView.findViewById(R.id.id_popup_site_cb_pg);
        mCbSG=(CheckBox)popupView.findViewById(R.id.id_popup_site_cb_sg);
        mCbSF=(CheckBox)popupView.findViewById(R.id.id_popup_site_cb_sf);
        mCbPF=(CheckBox)popupView.findViewById(R.id.id_popup_site_cb_pf);
        mCbCF=(CheckBox)popupView.findViewById(R.id.id_popup_site_cb_cf);
        mSiteSave=(TextView)popupView.findViewById(R.id.id_popup_site_save);

        mSitePopup.setTouchable(true);
        mSitePopup.setOutsideTouchable(true);
        mSitePopup.setFocusable(true);
        mSitePopup.setBackgroundDrawable(new ColorDrawable());
    }

    private void initCircleImage(CircleImage view)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.mipmap.x_3);
        view.setBitmap(bitmap);
    }

    private void initEvents()
    {
        mLyName.setOnClickListener(this);
        mLySite.setOnClickListener(this);
        mLySex.setOnClickListener(this);
        mLyAge.setOnClickListener(this);

        mLyPopupSexMan.setOnClickListener(this);
        mLyPopupSexWoman.setOnClickListener(this);
        mRbWoman.setOnClickListener(this);
        mRbMan.setOnClickListener(this);

        mSiteSave.setOnClickListener(this);
    }

    private void fillViews()
    {
        if(userInfo.getUserPetName()!=null)
        {
            mName.setText(userInfo.getUserPetName());
        }
        if(userInfo.getUserSex()!=null)
        {
            mSex.setText(userInfo.getUserSex());
        }
        if(userInfo.getUserAge()>0)
        {
            mAge.setText(userInfo.getUserAge());
        }
        if(userInfo.getUserPosition()!=null)
        {
            String[] sites=userInfo.getUserPosition().split(":");
            if(sites.length==1)
            {
                mSite.setText(sites[0]);
            }
            else if(sites.length==2)
            {
                mSite.setText(sites[0]+"、"+sites[1]);
            }
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_setting_ly_name:
                Intent name=new Intent(this,SettingNameActivity.class);
                startActivityForResult(name,99);
                break;
            case R.id.id_setting_ly_site:
                mSitePopup.showAtLocation(mLyAge,Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.id_setting_ly_sex:
                mSexPopup.showAtLocation(mLySex, Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.id_setting_ly_age:
                break;
            //sex
            case R.id.id_popup_sex_man:
                changeToSex(0);
                mSexPopup.dismiss();
                break;
            case R.id.id_popup_sex_woman:
                changeToSex(1);
                mSexPopup.dismiss();
                break;
            case R.id.id_popup_sex_rb_man:
                changeToSex(0);
                mSexPopup.dismiss();
                break;
            case R.id.id_popup_sex_rb_woman:
                changeToSex(1);
                mSexPopup.dismiss();
                break;
            case R.id.id_popup_site_save:
                mSitePopup.dismiss();
                setSite();
                break;

        }
    }

    //0:m 1:w
    private void changeToSex(int sex)
    {
        if(sex==0)
        {
            mRbMan.setChecked(true);
            mRbWoman.setChecked(false);
            sexString="男";
        }
        else if (sex==1)
        {
            mRbMan.setChecked(false);
            mRbWoman.setChecked(true);
            sexString="女";
        }
    }

    private void setSite()
    {
        StringBuilder builder=new StringBuilder();
        if(mCbPG.isChecked())
        {
            builder.append("PG:");
        }
        if(mCbSG.isChecked())
        {
            builder.append("SG:");
        }
        if(mCbCF.isChecked())
        {
            builder.append("CF:");
        }
        if (mCbPG.isChecked())
        {
            builder.append("PG");
        }
        if(mCbSF.isChecked())
        {
            builder.append("SF:");
        }
        siteString=builder.toString().substring(0,builder.length()-1);
        if(siteString.length()==0)
        {
            siteString="SG";
        }
    }

    private void updateUserInfo()
    {
        UserInfoAction action=new UserInfoAction(this);
        action.updateUserInfo(Config.userId,mName.getText().toString(),sexString,siteString,ageString);
    }

    private void getUserInfo(){
        UserInfoAction userAction = new UserInfoAction(this);
        userAction.GetUserInfo(Config.userId, new CallBackListener<UserInfoEntity>()
        {
            @Override
            public void onSuccess(UserInfoEntity result)
            {
                userInfo = result;
                fillViews();
            }

            @Override
            public void onFailure(String Message)
            {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==99 && resultCode==RESULT_OK)
        {
            String result=data.getStringExtra("username");
            mName.setText("solo:"+result);
        }
    }
}
