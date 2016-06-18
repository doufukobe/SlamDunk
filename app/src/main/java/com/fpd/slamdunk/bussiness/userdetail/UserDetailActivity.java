package com.fpd.slamdunk.bussiness.userdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.util.CircleImage;
import com.fpd.core.thum.ThumAction;
import com.fpd.core.userinfo.UserInfoAction;
import com.fpd.model.thum.ThumEntity;
import com.fpd.model.userinfo.UserInfoEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

/**
 * Created by t450s on 2016/6/16.
 */
public class UserDetailActivity extends CommenActivity {

    private TextView userName;
    private TextView userSexAge;
    private TextView thumNum;
    private TextView userPosition;
    private CircleImage userHeader;
    private RelativeLayout thumBtn;
    private String userId;
    private Button backBtn;
    private TextView topTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_layout);
        userId = getIntent().getStringExtra("USERID");
        getUserInfo();
        initView();
        setClick();
    }

    private void getUserInfo() {
        UserInfoAction action = new UserInfoAction(this);
        action.GetUserInfo(userId, new CallBackListener<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity result) {
                fullView(result);
            }

            @Override
            public void onFailure(String Message) {

            }
        });
    }

    private void fullView(UserInfoEntity result) {
        userName.setText(result.getUserPetName());
        userSexAge.setText(result.getUserAge()+"岁 "+result.getUserSex());
        userPosition.setText(result.getUserPosition());
        thumNum.setText(result.getUserLiked()+"");
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.invite_photo)
                .showImageForEmptyUri(R.mipmap.default_ball)
                .showImageOnFail(R.mipmap.invite_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
        userHeader.setBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.user_default_icon));
        ImageLoader.getInstance().loadImage(Config.headUrl + result.getUserHeadUrl(), options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                userHeader.setBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    private void initView() {
        userName = (TextView) findViewById(R.id.user_detail_name);
        userSexAge = (TextView) findViewById(R.id.user_detail_age);
        userPosition = (TextView) findViewById(R.id.user_detail_position);
        userHeader = (CircleImage) findViewById(R.id.user_detail_head);
        thumNum = (TextView) findViewById(R.id.user_detail_thumbs);
        thumBtn = (RelativeLayout) findViewById(R.id.user_detail_thumbtn);
        backBtn = (Button) findViewById(R.id.back_button);
        topTitle = (TextView) findViewById(R.id.top_title);
        topTitle.setText("个人详情");
    }

    private void setClick(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        thumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThumAction thumAction = new ThumAction(UserDetailActivity.this);
                thumAction.thumUp(userId, new CallBackListener<ThumEntity>() {
                    @Override
                    public void onSuccess(ThumEntity result) {
                        thumNum.setText(result.getUserLiked()+"");
                        thumBtn.setClickable(false);
                    }

                    @Override
                    public void onFailure(String Message) {
                        Toast.makeText(UserDetailActivity.this,Message,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
