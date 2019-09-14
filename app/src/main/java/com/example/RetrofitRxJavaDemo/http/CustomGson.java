package com.example.RetrofitRxJavaDemo.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CustomGson {

    public static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(String.class, new StringAdapter())
                .registerTypeAdapter(Integer.class, new IntegerAdapter())
                .registerTypeAdapter(DoubleAdapter.class, new DoubleAdapter())
                .registerTypeAdapter(Long.class, new LongAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
    }
}
