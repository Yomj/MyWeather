package com.yomj.myweather.myweather;

/**
 * Created by Administrator on 16-3-9.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
