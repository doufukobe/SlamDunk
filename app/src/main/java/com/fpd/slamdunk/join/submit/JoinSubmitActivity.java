package com.fpd.slamdunk.join.submit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.core.joinsubmit.JoinSubmitAction;
import com.fpd.model.success.SuccessEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
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
    private Button backBtn;
    private TextView textView;
    private TextView textUser;

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
        backBtn = (Button) findViewById(R.id.back_button);
        textView = (TextView) findViewById(R.id.top_title);
        textView.setText("申请加入");
        textUser = (TextView) findViewById(R.id.submit_user_id);
        textUser.setText(Config.userName);
    }

    private void setClick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean has = hasBall.isChecked();
                String ball = has?"True":"False";

                submitAction.submit(actId, Config.userId, introduce.getText().toString(), ball, new CallBackListener<SuccessEntity>() {
                    @Override
                    public void onSuccess(SuccessEntity result) {
                        SDDialog sdDialog = new SDDialog(JoinSubmitActivity.this,"申请成功",new dialogCallback());
                        sdDialog.show();
                    }

                    @Override
                    public void onFailure(String Message) {
                        Toast.makeText(JoinSubmitActivity.this,Message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private class dialogCallback implements SDDialog.Callback{

        @Override
        public void sureCallBack() {
            Intent intent = new Intent(JoinSubmitActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        @Override
        public void cancelCallBack() {

        }
    }

}
