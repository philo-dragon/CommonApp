package com.pfl.demo;

import androidx.lifecycle.MutableLiveData;

public class BaseRepository {

    protected <T> void startDialog(MutableLiveData<Resource<T>> liveData) {
        Resource<T> endDilaog = new Resource<>(Resource.LOADING, null, null);
        liveData.postValue(endDilaog);
    }

    protected <T> void dismissDialog(MutableLiveData<Resource<T>> liveData) {
        Resource<T> endDilaog = new Resource<>(Resource.END_LOADING, null, null);
        liveData.postValue(endDilaog);
    }

    protected void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
