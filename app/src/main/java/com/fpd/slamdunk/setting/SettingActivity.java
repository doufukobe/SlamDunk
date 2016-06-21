package com.fpd.slamdunk.setting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.dialog.MyDatePickerDialog;
import com.fpd.basecore.util.CircleImage;
import com.fpd.basecore.util.DensityUtil;
import com.fpd.core.userinfo.UserInfoAction;
import com.fpd.model.userinfo.UserInfoEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
import com.fpd.slamdunk.bussiness.selectimg.SelectHeadImgActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Calendar;

/**
 * Created by solo on 2016/6/11.
 */
public class SettingActivity extends CommenActivity implements View.OnClickListener
{
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
    private View mBack;

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

    private String nameString="";
    private String sexString="";
    private String siteString="";
    private String ageString="";

    private MyDatePickerDialog datePickerDialog;
    private TextView mSave;

    private int mScreenWidth;

    private UserInfoEntity userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        userInfo = (UserInfoEntity) getIntent().getSerializableExtra("userInfo");
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        initViews();
        initEvents();
        fillViews();
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

        mSave=(TextView)findViewById(R.id.id_setting_save_bt);
        mBack=findViewById(R.id.id_setting_back_ly);

        initCircleImage(mIcon);
        initSexPopupWindow();
        initSitePopupWindow();
        initDateDialog();
    }

    private void initSexPopupWindow()
    {
        View popupView= LayoutInflater.from(this).inflate(R.layout.popup_sex, null);
        mSexPopup=new PopupWindow(popupView,mScreenWidth-DensityUtil.dip2px(this,40),
                DensityUtil.dip2px(this,180),true);
        mLyPopupSexMan=popupView.findViewById(R.id.id_popup_sex_man);
        mLyPopupSexWoman=popupView.findViewById(R.id.id_popup_sex_woman);
        mRbMan=(RadioButton)popupView.findViewById(R.id.id_popup_sex_rb_man);
        mRbWoman=(RadioButton)popupView.findViewById(R.id.id_popup_sex_rb_woman);
        mSexPopup.setTouchable(true);
        mSexPopup.setOutsideTouchable(true);
        mSexPopup.setFocusable(true);
        mSexPopup.setBackgroundDrawable(new ColorDrawable());
        mSexPopup.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                setWindowBackgroundAlpha(1.0f);
            }
        });
    }

    private void initSitePopupWindow()
    {
        View popupView= LayoutInflater.from(this).inflate(R.layout.popup_site, null);
        mSitePopup=new PopupWindow(popupView,mScreenWidth-DensityUtil.dip2px(this,40),
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
        mSitePopup.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                setWindowBackgroundAlpha(1.0f);
            }
        });
    }

    private void initDateDialog()
    {
        Calendar calender= Calendar.getInstance();
        final int c_year = calender.get(Calendar.YEAR);
        final int c_month = calender.get(Calendar.MONTH);
        int c_day = calender.get(Calendar.DAY_OF_MONTH);
        datePickerDialog=new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                if(c_year-year<0)
                {
                    Toast.makeText(SettingActivity.this,"年龄设置不合法",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ageString=c_year-year+"";
                    mAge.setText(ageString);
                }

            }
        },c_year,c_month,c_day);
        datePickerDialog.setTitle(c_year + "年" + (c_month + 1) + "月" + c_day + "号");
    }

    private void initCircleImage(CircleImage view)
    {
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.mipmap.x_3);
        view.setBitmap(bitmap);
    }

    private void initEvents()
    {
        mIcon.setOnClickListener(this);
        mLyName.setOnClickListener(this);
        mLySite.setOnClickListener(this);
        mLySex.setOnClickListener(this);
        mLyAge.setOnClickListener(this);
        mLyPopupSexMan.setOnClickListener(this);
        mLyPopupSexWoman.setOnClickListener(this);
        mRbWoman.setOnClickListener(this);
        mRbMan.setOnClickListener(this);
        mSiteSave.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void fillViews()
    {
        if(userInfo==null) return;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.invite_photo)
                .showImageForEmptyUri(R.mipmap.default_ball)
                .showImageOnFail(R.mipmap.invite_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .resetViewBeforeLoading(true)
                .build();

        ImageLoader.getInstance().loadImage(Config.headUrl +userInfo.getUserHeadUrl(), options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {}
            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {}
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mIcon.setBitmap(bitmap);
            }
            @Override
            public void onLoadingCancelled(String s, View view) {}
        });
        if(userInfo.getUserPetName()!=null)
        {
            mName.setText(userInfo.getUserPetName());
            nameString=userInfo.getUserPetName();
        }
        if(userInfo.getUserSex()!=null)
        {
            mSex.setText(userInfo.getUserSex());
            sexString=userInfo.getUserSex();
        }
        if(userInfo.getUserAge()>=0)
        {
            mAge.setText(userInfo.getUserAge()+"");
            ageString=userInfo.getUserAge()+"";
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
            siteString=userInfo.getUserPosition();
        }
        if(userInfo.getUserName()!=null)
        {
            mAccount.setText(userInfo.getUserName());
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_setting_iv_icon:
                Intent icon=new Intent(this,SelectHeadImgActivity.class);
                startActivityForResult(icon,REQUESTCODE_SETICON);
                break;
            case R.id.id_setting_ly_name:
                Intent name=new Intent(this,SettingNameActivity.class);
                startActivityForResult(name,REQUESTCODE_SETNAME);
                break;
            case R.id.id_setting_ly_site:
                setCheckBoxFromSite(siteString);
                mSitePopup.showAtLocation(mLyAge, Gravity.CENTER, 0, 0);
                setWindowBackgroundAlpha(0.5f);
                break;
            case R.id.id_setting_ly_sex:
                setRadioButtonFromSex(sexString);
                mSexPopup.showAtLocation(mLySex, Gravity.CENTER,0,0);
                setWindowBackgroundAlpha(0.5f);
                break;
            case R.id.id_setting_ly_age:
                datePickerDialog.show();
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
            //site
            case R.id.id_popup_site_save:
                if(setSiteFromCheckBox())
                {
                    mSitePopup.dismiss();
                }
                break;
            case R.id.id_setting_save_bt:
                updateUserInfo();
                break;
            case R.id.id_setting_back_ly:
                finish();
                break;

        }
    }

    private void updateUserInfo()
    {
        Log.i("TAG","name="+nameString+" sex="+sexString+" age="+ageString+" site="+siteString);
        UserInfoAction action=new UserInfoAction(this);
        action.updateUserInfo(Config.userId, nameString, sexString, siteString, ageString, new CallBackListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                Intent intent=new Intent(SettingActivity.this, HomeActivity.class);
                intent.putExtra("selectPage",2);
                startActivity(intent);
                SettingActivity.this.finish();
            }

            @Override
            public void onFailure(String Message)
            {

            }
        });
    }

    private static final int REQUESTCODE_SETNAME=1;
    private static final int REQUESTCODE_SETICON=2;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode!=RESULT_OK) return;
        switch (requestCode)
        {
            case REQUESTCODE_SETNAME:
                String tmp=data.getStringExtra("USERNAME");
                if(tmp.length()>0)
                {
                    mName.setText(tmp);
                    nameString=tmp;
                }
                break;
            case REQUESTCODE_SETICON:
                Bitmap bitmap=data.getParcelableExtra("HEADIMG");
                mIcon.setBitmap(bitmap);
                break;
        }
    }

    private void setWindowBackgroundAlpha(float alpah)
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpah;
        getWindow().setAttributes(lp);
    }

    private CheckBox[] checkBoxes;
    private String[] allsites;
    private boolean setSiteFromCheckBox()
    {
        checkBoxes=new CheckBox[]{mCbPG,mCbSG,mCbSF,mCbPF,mCbCF};
        allsites=new String[]{"PG","SG","SF","PF","CF"};
        int count=0;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<5;i++)
        {
            if(checkBoxes[i].isChecked())
            {
                builder.append(allsites[i]+":");
                count++;
            }
        }

        if(count>2)
        {
            Toast.makeText(this,"位置不成超过两个,请重新选择",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(builder.length()==0)
        {
            siteString="SG";
        }else
        {
            siteString=builder.toString().substring(0, builder.length() - 1);
        }
        mSite.setText(siteString.replace(":", "、"));
        return true;
    }


    private void setCheckBoxFromSite(String siteString)
    {
        mCbPG.setChecked(false);
        mCbSG.setChecked(false);
        mCbSF.setChecked(false);
        mCbPF.setChecked(false);
        mCbCF.setChecked(false);
        if(siteString.contains("PG")) mCbPG.setChecked(true);
        if(siteString.contains("SG")) mCbSG.setChecked(true);
        if(siteString.contains("SF")) mCbSF.setChecked(true);
        if(siteString.contains("PF")) mCbPF.setChecked(true);
        if(siteString.contains("CF")) mCbCF.setChecked(true);
    }

    private void setRadioButtonFromSex(String sexString)
    {
        mRbMan.setChecked(false);
        mRbWoman.setChecked(false);
        if(sexString.equals("男"))
        {
            mRbMan.setChecked(true);
        }
        else if(sexString.equals("女"))
        {
            mRbWoman.setChecked(true);
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
        mSex.setText(sexString);
    }
}
