package com.fpd.basecore.application;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by t450s on 2016/6/1.
 */
public class BaseApplication extends Application {
    private static BaseApplication application;

    private static int screenHeight;
    private static int screenWidth;
    private AppManager appManager;

    public static BaseApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initScreen();
        appManager = AppManager.getAppManager();
    }



    private void initScreen() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public void exit(boolean isBackground){
        appManager.exitApp(this,isBackground);
    }
}
