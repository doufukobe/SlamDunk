package com.fpd.slamdunk.join.submit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.core.joinsubmit.JoinSubmitAction;
import com.fpd.model.arrange.ArrangeEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by t450s on 2016/6/6.
 */
public class JoinSubmitActivity extends CommenActivity {


    private CheckBox hasBall;
    private EditText introduce;
    private ButtonRectangle submit;
    private JoinSubmitAction submitAction;
    private String actId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_submit);
        initView();
        setClick();
        actId = getIntent().getStringExtra("ACTID");
        submitAction = new JoinSubmitAction(this);
    }

    private void initView() {
        hasBall = (CheckBox) findViewById(R.id.submit_checkbox);
        introduce = (EditText) findViewById(R.id.submit_introduce);
        submit = (ButtonRectangle) findViewById(R.id.submit_confirm);
    }

    private void setClick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean has = hasBall.isChecked();
                String ball = has?"True":"False";

                submitAction.submit("8", Config.userId, introduce.getText().toString(), ball, new CallBackListener<ArrangeEntity>() {
                    @Override
                    public void onSuccess(ArrangeEntity result) {
                        Toast.makeText(JoinSubmitActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String Message) {

                    }
                });
            }
        });
    }


}
