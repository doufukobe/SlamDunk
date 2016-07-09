package com.fpd.slamdunk.shake;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.application.BaseApplication;
import com.fpd.basecore.config.Config;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.basecore.util.DensityUtil;
import com.fpd.core.shake.ShakeAction;
import com.fpd.model.shake.StartStateEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by solo on 2016/6/25.
 */
public class ShakeActivity extends CommenActivity implements View.OnClickListener,SensorEventListener
{

    private View mBack;
    private ButtonRectangle mChoose;
    private ButtonRectangle mAccept;
    private ImageView mBall;

    private SensorManager sm;
    private Vibrator vibrator;
    private ShakeAction shakeAction;

    //定位相关
    private LocationClient mLocationClient;
    private double latitude;
    private double longitude;

    private Handler handler;
    private Runnable runnable;

    private int startState=0;
    private int actId;

    private TextView quickBt;

    private final int START_FLAG=0;
    private final int NO_START_FLAG=1;

    private String hasEquipment="False";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        shakeAction=new ShakeAction(this);
        handler=new Handler();
        runnable=new Runnable()
        {
            @Override
            public void run()
            {
                getStartState(NO_START_FLAG);
                handler.postDelayed(this,1000*60);
            }
        };

        initLocation();
        initViews();
        initEvents();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(sm!=null)
        {
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                    , SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(handler!=null)
        {
            handler.post(runnable);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(sm!=null)
        {
            sm.unregisterListener(this);
        }
        if(handler!=null)
        {
            handler.removeCallbacks(runnable);
        }
    }

    private void initViews()
    {
        mBack=findViewById(R.id.id_shake_back_ly);
        mChoose=(ButtonRectangle)findViewById(R.id.id_shake_choose);
        mAccept=(ButtonRectangle)findViewById(R.id.id_shake_accept);
        mBall=(ImageView)findViewById(R.id.id_shake_anim_ball);
        endHeight= BaseApplication.getScreenHeight()- DensityUtil.dip2px(this,75);

        quickBt=(TextView)findViewById(R.id.id_bt_start);
    }

    private void initEvents()
    {
        mBack.setOnClickListener(this);
        mChoose.setOnClickListener(this);
        mAccept.setOnClickListener(this);
        setBtBackground();

        quickBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.id_shake_back_ly:
                finish();
                break;
            case R.id.id_shake_choose:
                Intent intent=new Intent(this,ShakeChoseActivity.class);
                intent.putExtra("ACTID",actId+"");
                startActivity(intent);
                break;
            case R.id.id_shake_accept:
                Intent intent1=new Intent(this,ShakeAcceptActivity.class);
                intent1.putExtra("ACTID",actId+"");
                startActivity(intent1);
                break;
            case R.id.id_bt_start:
                getStartState(START_FLAG);
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER)
        {
            if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                    .abs(values[2]) > 17))
            {
                Log.i("TAG", "vibrate");
//                vibrator.vibrate(1000);
//                mLocationClient.start();
//                if(startState==0 || startState==4)
//                {
//                    anim();
//                    quickStart();
//                }
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    private void  initLocation(){
        mLocationClient=new LocationClient(this);
        mLocationClient.registerLocationListener(new ShakeLocation());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private class ShakeLocation implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation bdLocation)
        {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
                    bdLocation.getLocType() == BDLocation.TypeNetWorkLocation)
            {
                latitude=bdLocation.getLatitude();
                longitude=bdLocation.getLongitude();
//                anim();
                quickStart();
            }
            mLocationClient.stop();
        }
    }

    private void getStartState(final int flag)
    {
        shakeAction.quickState(Config.userId, new CallBackListener<StartStateEntity>()
        {
            @Override
            public void onSuccess(StartStateEntity result)
            {
                startState=result.getStartState();
                actId=result.getActId();
                Log.i("TAG", "startState=" + startState + " actId=" + actId);
                setBtBackground();
                if(flag==START_FLAG)
                {
                    Log.i("TAG", "START_FLAG");
                    if(startState==0 || startState==4)
                    {
                        SDDialog dialog=new SDDialog(ShakeActivity.this,"是否有球",new dialogCallback());
                        dialog.dialogSureText("有球");
                        dialog.dialogCancelText("无球");
                        dialog.show();
                    }
                    else if(startState==1 || startState==2 || startState==5)
                        Toast.makeText(ShakeActivity.this,"正在组队中",Toast.LENGTH_SHORT).show();
                    else if(startState==3)
                        Toast.makeText(ShakeActivity.this,"您目前已加入了一个活动",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String Message)
            {
                if(flag==START_FLAG)
                {
                    Toast.makeText(ShakeActivity.this,Message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void quickStart()
    {
        shakeAction.quickStart(Config.userId, hasEquipment, latitude, longitude,
                new CallBackListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {

                    }

                    @Override
                    public void onFailure(String Message)
                    {

                    }
                });
    }


    private void setBtBackground()
    {
        mChoose.setEnabled(false);
        mAccept.setEnabled(false);
        mChoose.setBackgroundColor(getResources().getColor(R.color.gray01));
        mAccept.setBackgroundColor(getResources().getColor(R.color.gray01));
        if(startState==0)
        {
            mChoose.setEnabled(true);
            mChoose.setBackgroundColor(getResources().getColor(R.color.colormain));

        }else if(startState==2)
        {
            mAccept.setEnabled(true);
            mAccept.setBackgroundColor(getResources().getColor(R.color.colormain));
        }
    }


    private ValueAnimator valueAnimator;
    private ObjectAnimator objectAnimator;
    private float endHeight;
    private void anim()
    {
        objectAnimator=ObjectAnimator.ofFloat(mBall,"alpha",1,0);
        objectAnimator.setDuration(1000);
        valueAnimator=ValueAnimator.ofFloat(0,endHeight);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float value=(float)animation.getAnimatedValue();
                mBall.setTranslationY(value);
                mBall.setAlpha(1.0f);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                objectAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
    }


    private class dialogCallback implements SDDialog.Callback{

        @Override
        public void sureCallBack() {
            hasEquipment="True";
            mLocationClient.start();
        }

        @Override
        public void cancelCallBack() {
            hasEquipment="False";
            mLocationClient.start();
        }
    }

}
