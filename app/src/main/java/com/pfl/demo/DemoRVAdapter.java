package com.pfl.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pfl.app.R;

import java.util.ArrayList;
import java.util.List;

public class DemoRVAdapter extends RecyclerView.Adapter<DemoRVAdapter.DemoViewHolder> {

    private List<String> mData = new ArrayList<>();

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {
        holder.textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<String> dadas) {
        mData = dadas;
        notifyDataSetChanged();
    }

    public void addData(List<String> dadas) {
        mData.addAll(dadas);
        notifyDataSetChanged();
    }

    static class DemoViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public DemoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvDemo);
        }
    }
}
