package com.fpd.slamdunk.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

/**
 * Created by solo on 2016/6/11.
 */
public class SettingNameActivity extends CommenActivity
{

    private TextView name;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_name);
        init();
    }

    private void init()
    {
        name=(TextView)findViewById(R.id.id_aty_setting_name);
        save=(Button)findViewById(R.id.id_aty_setting_bt);
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent();
                intent.putExtra("username",name.getText().toString());
                SettingNameActivity.this.setResult(RESULT_OK, intent);
                SettingNameActivity.this.finish();
            }
        });
    }
}
