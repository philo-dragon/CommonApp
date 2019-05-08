package com.pfl.demo;

public interface DemoResult<T> {

    void onSuccess(T t);

    void onFaile(int code, String msg);

}
