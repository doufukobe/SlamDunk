package com.fpd.basecore.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;
import java.util.Stack;

/**
 * Created by t450s on 2016/6/1.
 */
public class AppManager {
    private Stack<Activity> activityStack;
    private static volatile AppManager instance;

    private AppManager(){
        activityStack = new Stack<Activity>();
    }

    public static AppManager getAppManager(){
        if (instance == null){
            synchronized (AppManager.class){
                instance = new AppManager();
            }
        }
        return instance;
    }

    public void addActivity(Activity activity){
        activityStack.push(activity);
    }

    public Activity currentActivity(){
        if (!activityStack.isEmpty())
            return activityStack.peek();
        return null;
    }

    public void  finishActivity(){
        if (!activityStack.isEmpty()){
            Activity curr = activityStack.pop();
            curr.finish();
        }
    }

    public void removeActivity(Activity activity){
        if (!activityStack.isEmpty()){
            activityStack.remove(activity);
        }
    }

    public void finishAll(){
        if (!activityStack.isEmpty()){
            for (Activity  activity:activityStack){
                if (activity !=null){
                    activity.finish();
                }
            }
        }
        activityStack.clear();
    }

    public void exitApp(Context context,Boolean isBackground){
        finishAll();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        try{
            am.killBackgroundProcesses(context.getPackageName());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (!isBackground) {
                System.exit(0);
            }
        }
    }

    public boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) || info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
