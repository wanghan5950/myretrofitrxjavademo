package com.example.RetrofitRxJavaDemo.http;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 重用操作符
 */
public final class AppSchedulers {

    public static <T> ObservableTransformer<T, T> compose() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread());
    }
}
