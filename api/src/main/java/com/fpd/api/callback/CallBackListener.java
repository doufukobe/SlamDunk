package com.fpd.api.callback;

/**
 * Created by t450s on 2016/6/1.
 */
public interface CallBackListener<T> {
    public void onSuccess(T result);

    public void onFailure(String Message);
}
