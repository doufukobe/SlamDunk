package com.fpd.slamdunk.bussiness.login.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.login.widget.CircleImage;
import com.fpd.slamdunk.bussiness.login.widget.ColorIcon;


public class LoginActivity extends CommenActivity
{

    private TextView mTipRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initEvents();
    }

    private void initViews()
    {
        CircleImage mLoginIcon=(CircleImage)findViewById(R.id.id_login_user_icon);
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.mipmap.x_3);
        mLoginIcon.setBitmap(bitmap);

        mTipRegister=(TextView)findViewById(R.id.id_login_tip_register);
        initIcons();
    }

    private void initIcons()
    {
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
        mTipRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}
