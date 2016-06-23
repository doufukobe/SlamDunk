package com.fpd.slamdunk.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

/**
 * Created by solo on 2016/6/22.
 */
public class SettingAgeActivity extends CommenActivity implements View.OnClickListener
{

    private TextView save;
    private EditText age;
    private View back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_age);
        initViews();
        initEvents();
    }

    private void initViews()
    {
        save=(TextView)findViewById(R.id.id_setting_age_bt);
        age=(EditText)findViewById(R.id.id_setting_age_et);
        back=findViewById(R.id.id_setting_age_back_ly);

    }

    private void initEvents()
    {
        save.setOnClickListener(this);
        age.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_setting_age_back_ly:
                finish();
                break;
            case R.id.id_setting_age_bt:
                Intent intent = new Intent();
                intent.putExtra("USERAGE", age.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
}
