package com.kuangclub.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Woodslake on 2018/1/30.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("User-Agent", Header.UserAgent)
                .addHeader("Content-Type", Header.ContentType.Json)
                .build();
        Response response = chain.proceed(request);
        return response;
    }

    private static class Header {
        private static final String UserAgent = "kuangclub-android";

        private static class ContentType{
            private static final String Json = "application/json;charset=utf-8";
        }
    }
}
