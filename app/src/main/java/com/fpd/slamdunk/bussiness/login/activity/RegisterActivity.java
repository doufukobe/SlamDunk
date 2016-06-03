package com.fpd.slamdunk.bussiness.login.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.login.widget.ColorIcon;
import com.fpd.slamdunk.bussiness.login.widget.MyEditTextView;

/**
 * Created by solo on 2016/6/2.
 */
public class RegisterActivity extends CommenActivity implements
        View.OnClickListener,MyEditTextView.OnTextChangeListener
{

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
        initIcons();
    }


    private void initEvents()
    {

    }

    private void initIcons()
    {
        initIcon(R.id.id_register_name_icon,R.mipmap.ic_perm_identity_black_48dp);
        initIcon(R.id.id_register_password_icon,R.mipmap.ic_lock_open_black_48dp);
        initIcon(R.id.id_register_sure_icon,R.mipmap.ic_lock_open_black_48dp);
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

    }

    @Override
    public void onTextChange()
    {

    }
}
