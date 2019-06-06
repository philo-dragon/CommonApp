package com.pfl.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pfl.app.R;
import com.pfl.common.aop.SingleClick;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.utils.ToastUtils;

/**
 * UI控制层，这部分就是我们平时写的Activity和Fragment。
 */
public class DemoFragment extends BaseFragment {

    private DemoRVAdapter adapter;
    private DemoViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider.NewInstanceFactory().create(DemoViewModel.class);
        subscriber();
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }

    private void subscriber() {

        viewModel.getObserver().observe(this, listResource -> {
            switch (listResource.getStatus()) {
                case Resource.REFRESH:
                    adapter.setNewData(listResource.getData());
                    break;
                case Resource.LOADMORE:
                    adapter.addData(listResource.getData());
                    break;
            }
        });

        viewModel.getStateObserver().observe(this, resource -> {
            switch (resource.getStatus()) {
                case Resource.LOADING:
                    showDialog();
                    break;
                case Resource.END_LOADING:
                    dismissDialog();
                    break;
                case Resource.ERROR:
                    ToastUtils.Companion.makeShortToast("ERROR");
                    break;
                default:
                    ToastUtils.Companion.makeShortToast("其他错误");
                    break;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnRefresh).setOnClickListener(new View.OnClickListener() {
            // 如果需要自定义点击时间间隔，自行传入毫秒值即可
            // @SingleClick(2000)
            @SingleClick(5000)
            @Override
            public void onClick(View v) {
                viewModel.refresh();
            }
        });
        view.findViewById(R.id.btnLoadMore).setOnClickListener(new View.OnClickListener() {
            // 如果需要自定义点击时间间隔，自行传入毫秒值即可
            // @SingleClick(2000)
            @SingleClick(5000)
            @Override
            public void onClick(View v) {
                viewModel.loadMore();
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DemoRVAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.refresh();
    }
}
