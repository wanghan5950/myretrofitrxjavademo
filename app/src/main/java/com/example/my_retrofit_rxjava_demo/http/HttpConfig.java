package com.example.my_retrofit_rxjava_demo.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HttpUrl管理类
 */
public class HttpConfig {

    //项目接口 base url
    private static final String BASE_URL = "https://api.apiopen.top/";
    //默认超时时间
    private static final int DEFAULT_TIME_OUT = 30;

    public static OkHttpClient client = new OkHttpClient.Builder()
            //设置连接超时时间
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            //设置读取超时时间
            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            //设置写入超时时间
            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            //添加日志拦截器
            .addInterceptor(new LogInterceptor())
            .build();

    public static HttpAPI httpAPI = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            //配置OkHttp
            .client(client)
            //添加Gson转换器
            .addConverterFactory(GsonConverterFactory.create())
            //添加RxJava适配器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(HttpAPI.class);
}
