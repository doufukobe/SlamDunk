package com.fpd.slamdunk.bussiness.register.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.core.register.RegisterAction;
import com.fpd.model.login.LREntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.basecore.util.ColorIcon;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
import com.fpd.slamdunk.bussiness.login.widget.MyEditTextView;
import com.fpd.slamdunk.setting.FirstSettingActivity;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by solo on 2016/6/2.
 */
public class RegisterActivity extends CommenActivity implements
        View.OnClickListener,MyEditTextView.OnTextChangeListener
{

    private MyEditTextView mEtName;
    private MyEditTextView mEtPassword;
    private MyEditTextView mEtSure;
    private Button mBtRegister;
    private Button backbtn;
    private TextView topTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initEvents();
    }

    private void initViews()
    {
        mEtName=(MyEditTextView)findViewById(R.id.id_register_name_et);
        mEtPassword=(MyEditTextView)findViewById(R.id.id_register_password_et);
        mEtSure=(MyEditTextView)findViewById(R.id.id_register_sure_et);
        mBtRegister=(Button)findViewById(R.id.id_register_bt);
        backbtn = (Button) findViewById(R.id.back_button);
        topTitle = (TextView) findViewById(R.id.top_title);
        topTitle.setText("注册");
        initIcons();
    }


    private void initEvents()
    {
        mBtRegister.setOnClickListener(this);
        backbtn.setOnClickListener(this);
    }

    private void initIcons()
    {
        initIcon(R.id.id_register_name_icon,R.mipmap.ic_perm_identity_black_48dp);
        initIcon(R.id.id_register_password_icon, R.mipmap.ic_lock_open_black_48dp);
        initIcon(R.id.id_register_sure_icon, R.mipmap.ic_lock_open_black_48dp);
    }

    private void initIcon(int viewId,int resId)
    {
        ColorIcon mView = (ColorIcon) findViewById(viewId);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), resId);
        mView.setColor(this.getResources().getColor(R.color.gray01));
        mView.setBitmap(bitmap);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_register_bt:
                String name=mEtName.getText().toString();
                String password=mEtPassword.getText().toString();
                String sure=mEtSure.getText().toString();

                //用户名和密码都符合格式要求
                if(!password.equals(sure))
                {
                    showToast("两次输入的密码不一样");
                    return;
                }
                //将用户名和密码提交给后台
                submit(name,password);
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }

    @Override
    public void onTextChange()
    {

    }

    private void submit(String name,String password)
    {
        RegisterAction action=new RegisterAction(this);
        action.action(name, password, new CallBackListener<LREntity>()
        {
            @Override
            public void onSuccess(LREntity result)
            {
                Config.userId = result.getUserId()+"";
                Log.d("userId",Config.userId);
                getSharedPreferences(Config.sharedParaferance, MODE_PRIVATE)
                        .edit().putString(Config.USER,result.getUserId()+"").commit();
                JPushInterface.resumePush(RegisterActivity.this);
                    Intent intent = new Intent(RegisterActivity.this, FirstSettingActivity.class);
                    startActivity(intent);
                    finish();
            }

            @Override
            public void onFailure(String Message)
            {
               Toast.makeText(RegisterActivity.this,Message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showToast(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
