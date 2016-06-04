package com.fpd.slamdunk.bussiness.register.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fpd.api.callback.CallBackListener;
import com.fpd.core.register.RegisterAction;
import com.fpd.model.login.LREntity;
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

    private MyEditTextView mEtName;
    private MyEditTextView mEtPassword;
    private MyEditTextView mEtSure;
    private Button mBtRegister;
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
        initIcons();
    }


    private void initEvents()
    {
        mBtRegister.setOnClickListener(this);
    }

    private void initIcons()
    {
        initIcon(R.id.id_register_name_icon,R.mipmap.ic_perm_identity_black_48dp);
        initIcon(R.id.id_register_password_icon,R.mipmap.ic_lock_open_black_48dp);
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
                String nameRex="[^\\s]{6,10}";
                String passwordRex="[^\\s]{6,16}";
                String name=mEtName.getText().toString();
                String password=mEtPassword.getText().toString();
                String sure=mEtSure.getText().toString();
                if(!name.matches(nameRex))
                {
                    showToast("用户名格式错误，请输入6~10位的数字、字母或符号");
                    return;
                }
                if(!password.matches(passwordRex))
                {
                    showToast("密码格式错误，请输入6~16位的数字、字母或符号");
                    return;
                }

                //用户名和密码都符合格式要求
                if(!password.equals(sure))
                {
                    showToast("两次输入的密码不一样");
                    return;
                }
                //将用户名和密码提交给后台
//                submit(name,password);
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
                //注册成功跳转到首页
            }

            @Override
            public void onFailure(String Message)
            {
                //注册失败，告诉用户原因(用户名已存在)
            }
        });
    }

    private void showToast(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
