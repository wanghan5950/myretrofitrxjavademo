package com.example.RetrofitRxJavaDemo.http;

import android.support.annotation.Nullable;

public class Utils {

    static <T> T checkNull(@Nullable T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
