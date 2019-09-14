package com.example.RetrofitRxJavaDemo.http;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求头的拦截器，可以所有接口共有的请求参数添加到请求头
 */
public class HeadInterceptor implements Interceptor {

    @Override
    public Response intercept(@Nullable Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("", "")
                .build();
        return chain.proceed(request);
    }
}
