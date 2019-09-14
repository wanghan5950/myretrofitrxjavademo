package com.example.RetrofitRxJavaDemo.http;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomerResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private Type type;

    public CustomerResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string();
        Result result = new Result();
        int code;
        String msg;
        try {
            JSONObject jsonObject = new JSONObject(body);
            code = jsonObject.optInt("code");
            msg = jsonObject.optString("msg");
            if (code == 200) {
                result = gson.fromJson(body, type);
            } else {
                result.setCode(code);
                result.setMessage(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return (T) result;
    }
}
