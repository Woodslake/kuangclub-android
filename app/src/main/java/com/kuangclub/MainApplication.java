package com.kuangclub;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.kuangclub.util.Logger;

/**
 * Created by Woodslake on 2018/7/27.
 */
public class MainApplication extends Application {
    private String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.log(TAG, "onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.log(TAG, "onTerminate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.log(TAG, "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Logger.log(TAG, "onTrimMemory");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Logger.log(TAG, "attachBaseContext");
        MultiDex.install(base);
    }

}
