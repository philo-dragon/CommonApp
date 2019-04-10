package com.pfl.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.pfl.app.R;

/**
 * UI控制层，这部分就是我们平时写的Activity和Fragment。
 */
public class DemoFragment extends Fragment {
    DemoViewModel viewModel;

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
                    Toast.makeText(getActivity().getApplicationContext(), "LOADING", Toast.LENGTH_SHORT).show();
                    break;
                case Resource.REFRESH:
                    Toast.makeText(getActivity().getApplicationContext(), "REFRESH", Toast.LENGTH_SHORT).show();
                    break;
                case Resource.LOADMORE:
                    Toast.makeText(getActivity().getApplicationContext(), "LOADMORE", Toast.LENGTH_SHORT).show();
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
