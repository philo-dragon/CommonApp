package com.pfl.demo;

import androidx.lifecycle.MutableLiveData;
import com.pfl.common.utils.A;
import com.pfl.common.utils.ThreadPoolUtil;

import java.util.List;

/**
 * 仓库层，仓库层要做的工作是自主判断接口请求的数据应该是从数据库中读取还是从网络中获取，
 * 并将数据返回给调用方。
 * 如果是从网络中获取的话还要将这些数据存入到数据库当中，以避免下次重复从网络中获取。简而言之，
 * 仓库的工作就是在本地和网络数据之间做一个分配和调度的工作，调用方不管你的数据是从何而来的，我只是要从你仓库这里获取数据而已，
 * 而仓库则要自主分配如何更好更快地将数据提供给调用方。
 */
class DemoRepository extends BaseRepository {

    private DemoLocalData localData;
    private DemoNetworkData networkData;
    private MutableLiveData<Resource<List<String>>> liveData;

    DemoRepository(MutableLiveData<Resource<List<String>>> observer, DemoLocalData localData, DemoNetworkData networkData) {
        this.liveData = observer;
        this.localData = localData;
        this.networkData = networkData;
    }

    void getData(int page) {
        startDialog(liveData);

        ThreadPoolUtil.INSTANCE
                .getExecutordiskIO()
                .execute(() -> {

                    sleep(500);

                    dismissDialog(liveData);

                    sleep(100);

                    if (page == 1) {
                        List<String> data = networkData.getData();
                        Resource<List<String>> network = new Resource<>(Resource.REFRESH, data, null);
                        localData.save(data);
                        liveData.postValue(network);
                    } else {
                        List<String> data = networkData.getData();
                        Resource<List<String>> network = new Resource<>(Resource.LOADMORE, data, null);
                        liveData.postValue(network);
                    }

                });
    }

}
