package com.pfl.demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel基类
 * 封装一些常用方法
 */
public class BaseViewModel extends ViewModel {

    protected int page = 1;

    private MutableLiveData<Resource> stateObserver = new MutableLiveData<>();

    protected LiveData<Resource> getStateObserver() {
        return stateObserver;
    }

    protected void startDialog() {
        Resource endDilaog = new Resource<>(Resource.LOADING, null, null);
        stateObserver.postValue(endDilaog);
    }

    protected void dismissDialog() {
        Resource endDilaog = new Resource<>(Resource.END_LOADING, null, null);
        stateObserver.postValue(endDilaog);
    }

    protected void errorMsg(int state, String msg) {
        Resource endDilaog = new Resource<>(state, null, msg);
        stateObserver.postValue(endDilaog);
    }
}
