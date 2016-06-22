package com.fpd.slamdunk.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

/**
 * Created by solo on 2016/6/11.
 */
public class SettingNameActivity extends CommenActivity implements View.OnClickListener
{

    private EditText name;
    private TextView save;
    private View back;
    private int MAX_LENGTH=12;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_name);
        init();
    }

    private void init()
    {
        name=(EditText)findViewById(R.id.id_aty_setting_name);
        save=(TextView)findViewById(R.id.id_aty_setting_bt);
        back=findViewById(R.id.id_settingname_back_ly);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        name.addTextChangedListener(new TextWatcher()
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
                String str = name.getText().toString();
                String cutString = cutStringByByte(str);
                if (cutString == null)
                {
                    name.setSelection(str.length());
                    return;
                }
                name.setText(cutString);
                name.setSelection(cutString.length());

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_settingname_back_ly:
                finish();
                break;
            case R.id.id_aty_setting_bt:
                Intent intent = new Intent();
                intent.putExtra("USERNAME", name.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private String cutStringByByte(String str)
    {
        if(str==null) return null;
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
