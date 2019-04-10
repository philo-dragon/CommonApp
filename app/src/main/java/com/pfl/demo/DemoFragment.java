package com.pfl.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.pfl.app.R;
import com.pfl.common.base.BaseFragment;

import java.util.List;

/**
 * UI控制层，这部分就是我们平时写的Activity和Fragment。
 */
public class DemoFragment extends BaseFragment {

    private DemoViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider.NewInstanceFactory().create(DemoViewModel.class);
        registObserver();
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }

    private void registObserver() {

        viewModel.getObserver().observe(this, listResource -> {

            switch (listResource.getStatus()) {
                case Resource.LOADING:
                    showDialog();
                    break;
                case Resource.END_LOADING:
                    dismissDialog();
                    break;
                case Resource.REFRESH:
                    List<String> data = listResource.getData();
                    StringBuilder sb = new StringBuilder();
                    for (String datum : data) {
                        sb.append(datum).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    Toast.makeText(getActivity().getApplicationContext(), "REFRESH " + sb.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case Resource.LOADMORE:
                    data = listResource.getData();
                    sb = new StringBuilder();
                    for (String datum : data) {
                        sb.append(datum).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    Toast.makeText(getActivity().getApplicationContext(), "LOADMORE " + sb.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case Resource.ERROR:
                    Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    break;
            }

        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnRefresh).setOnClickListener(v -> viewModel.refresh());

        view.findViewById(R.id.btnLoadMore).setOnClickListener(v -> viewModel.loadMore());

    }
}
