package com.kuangclub.http;

import com.kuangclub.util.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Woodslake on 2018/1/31.
 */

public class HttpLogging {

    public final HttpLoggingInterceptor httpLoggingInterceptor;

    public HttpLogging() {
        httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {

        @Override
        public void log(String message) {
            Logger.log("HttpLogging", message);
        }

    }

}
