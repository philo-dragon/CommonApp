package com.pfl.demo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * ViewModel用于持有和UI元素相关的数据，以保证这些数据在屏幕旋转时不会丢失，以及负责和仓库之间进行通讯。
 */
public class DemoViewModel extends BaseViewModel {

    private DemoRepository repository;
    private MutableLiveData<Resource<List<String>>> observer;

    public DemoViewModel() {
        repository = new DemoRepository();
        observer = new MutableLiveData<>();
    }

    LiveData<Resource<List<String>>> getObserver() {
        return observer;
    }

    void refresh() {
        page = 1;
        getData(page);
    }

    void loadMore() {
        page++;
        getData(page);
    }

    private void getData(int page) {
        startDialog();
        repository.getData(page, new DemoResult<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                dismissDialog();
                Resource<List<String>> datas;
                if (page == 1) {
                    datas = new Resource<>(Resource.REFRESH, strings, null);
                } else {
                    datas = new Resource<>(Resource.LOADMORE, strings, null);
                }
                observer.postValue(datas);
            }

            @Override
            public void onFaile(int code, String msg) {
                dismissDialog();
                errorMsg(code,msg);
            }
        });
    }

}
