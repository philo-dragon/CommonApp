package com.pfl.demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * ViewModel用于持有和UI元素相关的数据，以保证这些数据在屏幕旋转时不会丢失，以及负责和仓库之间进行通讯。
 */
public class DemoViewModel extends ViewModel {

    private int page = 1;
    private MutableLiveData<Resource<List<String>>> observer = new MutableLiveData<>();
    private DemoRepository repository;

    public DemoViewModel() {
        repository = new DemoRepository(observer, new DemoLocalData(), new DemoNetworkData());
    }

    public LiveData<Resource<List<String>>> getObserver() {
        return observer;
    }

    public void refresh() {
        page = 1;
        repository.getData(page);
    }

    public void loadMore() {
        page++;
        repository.getData(page);
    }

}
