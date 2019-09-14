package com.example.RetrofitRxJavaDemo.http;

import com.google.gson.annotations.SerializedName;

/**
 * 请求结果统一格式，根据自己的项目来修改
 * 一般会将主要的数据放在Data中，这样不同的接口创建对应Data的实体类即可
 * @param <T>
 */
public class Result<T> {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private T Data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
