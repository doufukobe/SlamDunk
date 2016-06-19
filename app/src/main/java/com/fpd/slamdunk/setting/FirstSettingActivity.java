package com.fpd.slamdunk.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.util.CircleImage;
import com.fpd.core.userinfo.UserInfoAction;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
import com.fpd.slamdunk.bussiness.selectimg.SelectHeadImgActivity;

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
    private View mLyIcon;
    private TextView mSave;
    private EditText mAge;

    private String nameString;
    private String sexString;
    private String ageString;
    private String siteString;

    private int MAX_LENGTH=20;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_setting);
        initViews();
        initEvents();
        fillViews();
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
        mLyIcon=findViewById(R.id.id_first_setting_icon_ly);
        mSave=(TextView)findViewById(R.id.id_first_setting_save);
        mAge=(EditText)findViewById(R.id.id_first_setting_age_et);
    }

    private void initEvents()
    {
        mLyIcon.setOnClickListener(this);
        mSave.setOnClickListener(this);

        mRbMan.setOnClickListener(this);
        mRbWoman.setOnClickListener(this);

        mCbPG.setOnClickListener(this);
        mCbSG.setOnClickListener(this);
        mCbSF.setOnClickListener(this);
        mCbPF.setOnClickListener(this);
        mCbCF.setOnClickListener(this);

        mName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String str = mName.getText().toString();
                String cutString = cutStringByByte(str);
                if (cutString == null)
                {
                    mName.setSelection(str.length());
                    return;
                }
                mName.setText(cutString);
                mName.setSelection(cutString.length());

            }
        });
    }

    private static final int REQUESTCODE_SETICON=1;
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_first_setting_icon_ly:
                Intent icon=new Intent(this, SelectHeadImgActivity.class);
                startActivityForResult(icon, REQUESTCODE_SETICON);
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

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return;
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
        Log.i("TAG", "userId="+Config.userId+" name=" + nameString + " sex=" + sexString + " age=" + ageString + " site=" + siteString);
        UserInfoAction action=new UserInfoAction(this);
        action.updateUserInfo(Config.userId, nameString, sexString, siteString, ageString,
                new CallBackListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        Intent intent=new Intent(FirstSettingActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String Message)
                    {

                    }
                });
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
        String ageReg="([0-9])|([1-9][0-9])|100";
        nameString=mName.getText().toString().trim();
        ageString=mAge.getText().toString().trim();
        setSiteFromCheckBox();
        if(nameString==null || nameString.length()==0)
        {
            nameString="Curry";
        }
        if(sexString==null)
        {
            sexString="男";
        }
        if(ageString==null || !ageString.matches(ageReg))
        {
            ageString="1";
        }
        if(siteString==null || siteString.equals(""))
        {
            siteString="PG";
        }
    }

    private void fillViews()
    {
        mName.setText("Curry");
        mAge.setText("1");
        mRbMan.setChecked(true);
        mCbPG.setChecked(true);
    }

    @Override
    public void onBackPressed()
    {
        setUserInfo();
        updateUserInfo();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private String cutStringByByte(String str)
    {
        String result;
        int count=0;
        int end=0;
        byte[] bt = null;
        int len=MAX_LENGTH;
        try
        {
            bt=str.getBytes("GBK");
        }
        catch (Exception e){}
        if(bt==null || bt.length<=len) return null;
        for(int i=0;i<len;i++)
        {
            if(bt[i]>0) count++;
            else count+=100;
        }
        end=(count/100)/2+count%100;
        result = str.substring(0,end);
        return result;
    }
}
