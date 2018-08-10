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
        boolean isConnected = NetworkUtil.isConnected(context);
        Request request = chain.request();
        if(isConnected){
            request = request
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            Logger.log("HttpCache", "network isConnected, force network.");
        }else{
            request = request
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Logger.log("HttpCache", "no network, force cache.");
        }
        Response response = chain.proceed(request);
        if(isConnected){
            int maxAge = 10;
            response = response
                    .newBuilder()
                    .removeHeader("Pragma") //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        }else{
            int maxStale = 60 * 60 * 24;
            response = response
                    .newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
