package com.kuangclub.http;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Woodslake on 2018/1/17.
 */

public class ServiceFactory {
    private static OkHttpClient okHttpClient = null;
    private static Retrofit retrofit = null;

    private static OkHttpClient getOkHttpClient(Context context){
        if(okHttpClient == null){
            File cacheDirectory = new File(context.getCacheDir(), "HttpCache");
            int cacheSize = 10 * 1024 * 1024; //10M
            Cache cache = new Cache(cacheDirectory, cacheSize);
            okHttpClient = new OkHttpClient.Builder()
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .cache(cache)
                    .addInterceptor(new HeaderInterceptor())
                    .addInterceptor(new CacheInterceptor(context))
                    .addInterceptor(new HttpLogging().httpLoggingInterceptor)
                    .cookieJar(new CookieManager())
                    .build();
        }
        return okHttpClient;
    }

    private static Retrofit getRetrofit(Context context){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient(context))
                    .baseUrl(Server.ApiHost)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <T> T createService(Context context, Class<T> clazz){
        return getRetrofit(context).create(clazz);
    }

}
