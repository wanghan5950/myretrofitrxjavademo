package com.example.my_retrofit_rxjava_demo.http;

import android.util.ArrayMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * Http Observable管理
 */
public class HttpManager {

    private static Map<String, Disposable> disposableMap = new ArrayMap<>();

    private static Set<String> successSet = new HashSet<>();

    private static HttpManager httpManager;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }

    /**
     * 添加Observable
     */
    public void add(String tag, Disposable disposable) {
        if (disposableMap == null) {
            disposableMap = new ArrayMap<>();
        }
        disposableMap.put(tag, disposable);
    }

    /**
     * 根据tag取消订阅Observable
     * @param tag
     */
    public void disposeSingle(String tag) {
        Disposable disposable = disposableMap.get(tag);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposableMap.remove(tag);
        }
    }

    /**
     * 取消订阅指定tag外的其他Observable
     */
    public void disposeOther(String tag) {
        Iterator<Map.Entry<String, Disposable>> it = disposableMap.entrySet().iterator();
        while (it.hasNext()) {
            Disposable disposable = it.next().getValue();
            String key = it.next().getKey();
            if (!key.equals(tag) && disposable != null) {
                disposable.dispose();
                disposableMap.remove(key);
            }
        }
    }

    /**
     * 取消订阅所有Observable
     */
    public void disposeAll() {
        for (Disposable disposable : disposableMap.values()) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposableMap.clear();
    }

    public static String recordMethodName() {
        StackTraceElement[] s = Thread.currentThread().getStackTrace();
        return s[3].getMethodName();
    }

    public void addSuccess(String tag) {
        if (successSet == null) {
            successSet = new HashSet<>();
        }
        successSet.add(tag);
    }
}
