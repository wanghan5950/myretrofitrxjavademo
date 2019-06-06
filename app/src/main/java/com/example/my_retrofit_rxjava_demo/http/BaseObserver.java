package com.example.my_retrofit_rxjava_demo.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<Result<T>> {

    private String tag;

    public BaseObserver() {
    }

    public BaseObserver(String tag) {
        if (tag == null) {
            this.tag = "";
        } else {
            this.tag = tag;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (NetworkUtil.isConnected()) {
            HttpManager.getInstance().add(tag, d);
        } else {
            d.dispose();
            onFailure("网络未连接");
        }
    }

    @Override
    public void onNext(Result<T> result) {
        if (result.getCode() == 200) {
            //请求成功
            onSuccess(result.getData(), result.getMessage());
            HttpManager.getInstance().addSuccess(tag);
        } else {
            //请求失败
            onFailure(result.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        HttpManager.getInstance().disposeSingle(tag);
    }

    protected abstract void onSuccess(T data, String msg);

    protected abstract void onFailure(String msg);
}
