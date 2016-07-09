package com.fpd.slamdunk.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fpd.basecore.config.Config;
import com.fpd.slamdunk.bussiness.home.activity.HomeActivity;
import com.fpd.slamdunk.bussiness.myactdetial.MyActDetailActivity;
import com.fpd.slamdunk.bussiness.startup.StartUpActivity;
import com.fpd.slamdunk.shake.ShakeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by t450s on 2016/6/6.
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //processCustomMessage(context, bundle);
            Log.d(TAG,   bundle.getString(JPushInterface.EXTRA_EXTRA));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的title: " + bundle.getString(JPushInterface.EXTRA_TITLE));
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的消息: " +bundle.getString(JPushInterface.EXTRA_EXTRA));


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            Log.d(TAG,   bundle.getString(JPushInterface.EXTRA_EXTRA));
            //打开自定义的Activity
            if (isRunning(context, context.getPackageName())){

                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    return;
                }
                if (Config.userId.isEmpty()){
                    Intent i = new Intent(context, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("selectPage",0);
                    context.startActivity(i);
                }else{
                    int actId = praseJson(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    if(actId==-1)
                    {
                        Intent intent1=new Intent(context, ShakeActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    } else if (actId !=0){
                        Intent i = new Intent(context, MyActDetailActivity.class);
                        intent.putExtra("ACTSTATE","1");
                        i.putExtra("ACTID",actId+"");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }else{
                        Intent i = new Intent(context, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("selectPage",0);
                        context.startActivity(i);
                    }
                }

            }else{
                Intent i = new Intent(context, StartUpActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    private int praseJson(String s){
        try {
            JSONObject json = new JSONObject(s);
            return json.getInt("actId");
        } catch (JSONException e) {
            Log.e(TAG, "Get message extra JSON error!");
        }
        return 0;
    }

    private boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) || info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void killAll(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        try{
            am.killBackgroundProcesses(context.getPackageName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
