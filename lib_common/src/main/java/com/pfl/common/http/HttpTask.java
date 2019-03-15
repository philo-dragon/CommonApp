package com.pfl.common.http;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest httpRequest;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, CallBackListener listener) {
        this.httpRequest = httpRequest;
        httpRequest.setUrl(url);
        httpRequest.setListener(listener);
        String content = new Gson().toJson(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            httpRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }


    private long delayTime;
    private int retryCount;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        delayTime = System.currentTimeMillis() + delayTime;
        this.delayTime = delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}
