package com.fpd.slamdunk.bussiness.startup;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpd.basecore.config.Config;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
import com.fpd.slamdunk.bussiness.login.activity.LoginActivity;

/**
 * Created by t450s on 2016/6/10.
 */
public class StartUpActivity extends CommenActivity implements Animator.AnimatorListener,Runnable{

    private ImageView img;
    private TextView name;
    private AnimatorSet set = new AnimatorSet();
    private SharedPreferences sp;
    private static final int LOACTION_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up_layout);
        init();
        checkPermission();
    }

    private void init(){
        img = (ImageView) findViewById(R.id.start_up_ball);
        name = (TextView) findViewById(R.id.start_up_text);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/title.ttf");
        name.setTypeface(typeface);
        name.setAlpha(0);
    }

    private void startAnimation(){

        ObjectAnimator img_In = ObjectAnimator.ofFloat(img, "translationY", -300, 0);
        img_In.setInterpolator(new BounceInterpolator());
        ObjectAnimator name_InRx = ObjectAnimator.ofFloat(name,"rotationX",0,720);
        ObjectAnimator name_InSx = ObjectAnimator.ofFloat(name,"scaleX",0,1);
        ObjectAnimator name_InRy = ObjectAnimator.ofFloat(name,"rotationY",0,720);
        ObjectAnimator name_InSy = ObjectAnimator.ofFloat(name, "scaleY", 0, 1);

        img_In.setDuration(1500).start();
        img_In.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                name.setAlpha(1);
                set.setDuration(1500).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        set.play(name_InRx).with(name_InSx).with(name_InRy).with(name_InSy);
        set.addListener(this);
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
       Handler handler = new Handler();
        handler.postDelayed(StartUpActivity.this,1000);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    private void skipActivity(){
        sp =  this.getSharedPreferences(Config.sharedParaferance,MODE_PRIVATE);
        if (sp.getString(Config.userId,"").isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("ACTIVITYFROM", "StartUpActivity");
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, HomeActivity.class);
            Config.userId = sp.getString(Config.userId,"");
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void run() {
        skipActivity();
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    LOACTION_REQUEST_CODE);
        }else{
            startAnimation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOACTION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
            startAnimation();
        }
    }
}
