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
    private LiveData<Resource<List<String>>> observer;
    private DemoRepository repository;

    public DemoViewModel() {
        repository = new DemoRepository(new DemoLocalData(), new DemoNetworkData());
        observer = repository.getData(1);
    }

    public LiveData<Resource<List<String>>> getObserver() {
        return observer;
    }

    public void refresh() {
        page = 1;
        repository.refresh(page);
    }

    public void loadMore() {
        page++;
        repository.loadMore(page);
    }

}
