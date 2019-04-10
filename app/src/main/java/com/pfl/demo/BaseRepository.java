package com.pfl.demo;

import androidx.lifecycle.MutableLiveData;

public class BaseRepository<T> {

    public void startDialog(MutableLiveData<Resource<T>> liveData) {
        Resource<T> endDilaog = new Resource<>(Resource.LOADING, null, null);
        liveData.postValue(endDilaog);
    }

    public void dismissDialog(MutableLiveData<Resource<T>> liveData) {
        Resource<T> endDilaog = new Resource<>(Resource.END_LOADING, null, null);
        liveData.postValue(endDilaog);
    }

}
