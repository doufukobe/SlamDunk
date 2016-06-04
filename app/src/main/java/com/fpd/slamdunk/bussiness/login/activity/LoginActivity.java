package com.fpd.slamdunk.bussiness.login.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fpd.api.callback.CallBackListener;
import com.fpd.core.login.LoginAction;
import com.fpd.model.login.LREntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.login.widget.CircleImage;
import com.fpd.slamdunk.bussiness.login.widget.ColorIcon;
import com.fpd.slamdunk.bussiness.login.widget.MyEditTextView;
import com.fpd.slamdunk.bussiness.register.activity.RegisterActivity;


public class LoginActivity extends CommenActivity implements View.OnClickListener
{

    private TextView mTipRegister;
    private MyEditTextView mEtName;
    private MyEditTextView mEtPassword;
    private Button mBtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initEvents();
        initImages();
    }

    private void initViews()
    {
        mEtName=(MyEditTextView)findViewById(R.id.id_login_name_et);
        mEtPassword=(MyEditTextView)findViewById(R.id.id_login_password_et);
        mBtLogin=(Button)findViewById(R.id.id_login_bt);
        mTipRegister=(TextView)findViewById(R.id.id_login_tip_register);
    }

    private void initImages()
    {
        CircleImage mLoginIcon=(CircleImage)findViewById(R.id.id_login_user_icon);
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.mipmap.x_3);
        mLoginIcon.setBitmap(bitmap);
        initIcon(R.id.id_login_name_icon, R.mipmap.ic_perm_identity_black_48dp);
        initIcon(R.id.id_login_password_icon, R.mipmap.ic_lock_open_black_48dp);
    }

    private void initIcon(int viewId,int resId)
    {
        ColorIcon mView = (ColorIcon) findViewById(viewId);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), resId);
        mView.setColor(this.getResources().getColor(R.color.gray01));
        mView.setBitmap(bitmap);
    }

    private void initEvents()
    {
        mBtLogin.setOnClickListener(this);
        mTipRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_login_tip_register:
                Intent intentToRegister=new Intent(this, RegisterActivity.class);
                startActivity(intentToRegister);
                break;
            case R.id.id_login_bt:
                String nameRex="[^\\s]{6,10}";
                String name=mEtName.getText().toString();
                String password=mEtPassword.getText().toString();
                if(!name.matches(nameRex))
                {
                    showToast("用户名输入错误");
                    return;
                }
                //提交用户名和密码给后台
//                submit(name,password);
                break;
            case R.id.id_login_tip_forget:

                break;
        }
    }
    private void showToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void submit(String name,String password)
    {
        LoginAction action=new LoginAction(this);
        action.action(name, password, new CallBackListener<LREntity>()
        {
            @Override
            public void onSuccess(LREntity result)
            {
                //登陆成功跳转到首页
            }

            @Override
            public void onFailure(String Message)
            {
                //登陆失败，告诉用户原因(1、用户名输入错误，2、密码输入错误)
            }
        });
    }
}