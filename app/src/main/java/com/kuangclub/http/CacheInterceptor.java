package com.kuangclub.http;

import android.content.Context;

import com.kuangclub.util.Logger;
import com.kuangclub.util.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Woodslake on 2018/8/10.
 */
public class CacheInterceptor implements Interceptor {
    private Context context;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean isConnected = NetworkUtil.isConnected(context);
        if(!isConnected){
            request = request
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Logger.log("HttpCache", "no network, force cache.");
        }
        Response response = chain.proceed(request);
        return null;
    }
}
