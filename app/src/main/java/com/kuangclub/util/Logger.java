package com.kuangclub.util;

import android.text.TextUtils;
import android.util.Log;

import com.kuangclub.BuildConfig;

/**
 * Created by Woodslake on 2018/1/31.
 */

public class Logger {
    private static final boolean LOG_DEBUG = BuildConfig.LOG_DEBUG;

    public static void log(String tag, String message){
        if(LOG_DEBUG){
            if(!TextUtils.isEmpty(message)){
                Log.i(tag, message);
            }
        }
    }

    public static void warn(String tag, String message){
        if(LOG_DEBUG){
            if(!TextUtils.isEmpty(message)){
                Log.w(tag, message);
            }
        }
    }

    public static void error(String tag, String message){
        if(LOG_DEBUG){
            if(!TextUtils.isEmpty(message)){
                Log.e(tag, message);
            }
        }
    }

}
