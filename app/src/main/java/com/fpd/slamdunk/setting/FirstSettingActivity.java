package com.fpd.slamdunk.setting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fpd.basecore.dialog.MyDatePickerDialog;
import com.fpd.basecore.util.CircleImage;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
import com.fpd.slamdunk.bussiness.selectimg.SelectHeadImgActivity;

import java.util.Calendar;

/**
 * Created by solo on 2016/6/16.
 */
public class FirstSettingActivity extends CommenActivity implements View.OnClickListener
{
    private CircleImage mIcon;
    private EditText mName;
    private RadioButton mRbMan;
    private RadioButton mRbWoman;
    private CheckBox mCbPG;
    private CheckBox mCbSG;
    private CheckBox mCbSF;
    private CheckBox mCbPF;
    private CheckBox mCbCF;

    private View mLyAge;
    private View mLyIcon;
    private Button mSave;

    private String nameString;
    private String sexString;
    private String ageString;
    private String siteString;

    private MyDatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_setting);
        initViews();
        initEvents();
    }

    private void initViews()
    {
        mIcon=(CircleImage)findViewById(R.id.id_first_setting_icon_iv);
        mName=(EditText)findViewById(R.id.id_first_setting_name_et);
        mRbMan=(RadioButton)findViewById(R.id.id_first_setting_sex_man_rb);
        mRbWoman=(RadioButton)findViewById(R.id.id_first_setting_sex_woman_rb);
        mCbPG=(CheckBox)findViewById(R.id.id_first_setting_site_pg_cb);
        mCbSG=(CheckBox)findViewById(R.id.id_first_setting_site_sg_cb);
        mCbSF=(CheckBox)findViewById(R.id.id_first_setting_site_sf_cb);
        mCbPF=(CheckBox)findViewById(R.id.id_first_setting_site_pf_cb);
        mCbCF=(CheckBox)findViewById(R.id.id_first_setting_site_cf_cb);
        mLyAge=findViewById(R.id.id_first_setting_age_ly);
        mLyIcon=findViewById(R.id.id_first_setting_icon_ly);
        mSave=(Button)findViewById(R.id.id_first_setting_save);

        initDateDialog();
    }

    private void initEvents()
    {
        mLyIcon.setOnClickListener(this);
        mLyAge.setOnClickListener(this);
        mSave.setOnClickListener(this);

        mRbMan.setOnClickListener(this);
        mRbWoman.setOnClickListener(this);

        mCbPG.setOnClickListener(this);
        mCbSG.setOnClickListener(this);
        mCbSF.setOnClickListener(this);
        mCbPF.setOnClickListener(this);
        mCbCF.setOnClickListener(this);
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
                    Toast.makeText(FirstSettingActivity.this, "年龄设置不合法", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ageString=c_year-year+"";
                }

            }
        },c_year,c_month,c_day);
        datePickerDialog.setTitle(c_year + "年" + (c_month + 1) + "月" + c_day + "号");
    }

    private static final int REQUESTCODE_SETICON=1;
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_first_setting_icon_ly:
                Intent icon=new Intent(this, SelectHeadImgActivity.class);
                startActivityForResult(icon,REQUESTCODE_SETICON);
                break;
            case R.id.id_first_setting_age_ly:
                datePickerDialog.show();
                break;
            case R.id.id_first_setting_save:
                setUserInfo();
                updateUserInfo();
                break;
            case R.id.id_first_setting_sex_man_rb:
                changeToSex(1);
                break;
            case R.id.id_first_setting_sex_woman_rb:
                changeToSex(0);
                break;
            case R.id.id_first_setting_site_pg_cb:
                if(isSiteBeyond2()) mCbPG.setChecked(false);
                break;
            case R.id.id_first_setting_site_sg_cb:
                if(isSiteBeyond2()) mCbSG.setChecked(false);
                break;
            case R.id.id_first_setting_site_sf_cb:
                if(isSiteBeyond2()) mCbSF.setChecked(false);
                break;
            case R.id.id_first_setting_site_pf_cb:
                if(isSiteBeyond2()) mCbPF.setChecked(false);
                break;
            case R.id.id_first_setting_site_cf_cb:
                if(isSiteBeyond2()) mCbCF.setChecked(false);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUESTCODE_SETICON:
                Bitmap iconBitmap=data.getParcelableExtra("HEADIMG");
                mIcon.setBitmap(iconBitmap);
                break;
        }
    }

    private void updateUserInfo()
    {
        Log.i("TAG", "name=" + nameString + " sex=" + sexString + " age=" + ageString + " site=" + siteString);
//        UserInfoAction action=new UserInfoAction(this);
//        action.updateUserInfo(Config.userId, nameString, sexString, siteString, ageString);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    //0:woman 1:man
    private void changeToSex(int sex)
    {
        if(sex==0)
        {
            mRbWoman.setChecked(true);
            mRbMan.setChecked(false);
            sexString="女";
        }
        else if(sex==1)
        {
            mRbWoman.setChecked(false);
            mRbMan.setChecked(true);
            sexString="男";
        }
    }

    private CheckBox[] checkBoxes;
    private String[] allsites;
    private void setSiteFromCheckBox()
    {
        checkBoxes=new CheckBox[]{mCbPG,mCbSG,mCbSF,mCbPF,mCbCF};
        allsites=new String[]{"PG","SG","SF","PF","CF"};
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<5;i++)
        {
            if(checkBoxes[i].isChecked())
            {
                builder.append(allsites[i]+":");
            }
        }
        if(builder.length()!=0)
        {
            siteString=builder.toString().substring(0,builder.length()-1);
        }

    }

    private boolean isSiteBeyond2()
    {
        checkBoxes=new CheckBox[]{mCbPG,mCbSG,mCbSF,mCbPF,mCbCF};
        int count=0;
        for(int i=0;i<5;i++)
        {
            if(checkBoxes[i].isChecked()) count++;
        }
        return (count>2);
    }

    private void setUserInfo()
    {
        nameString=mName.getText().toString().trim();
        setSiteFromCheckBox();
        if(nameString!=null && nameString.length()==0)
        {
            nameString="Curry";
        }
        if(sexString==null)
        {
            sexString="男";
        }
        if(ageString==null)
        {
            ageString="1";
        }
        if(siteString==null || siteString.equals(""))
        {
            siteString="PG";
        }
    }

}
