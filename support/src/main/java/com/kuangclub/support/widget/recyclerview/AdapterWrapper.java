package com.kuangclub.support.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class AdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 10000;
    private static final int TYPE_FOOTER = 20000;

    private RecyclerView.Adapter adapter;

    private View headerView;
    private View footerView;

    public AdapterWrapper(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            return new ViewHolder(headerView);
        }else if(viewType == TYPE_FOOTER){
            return new ViewHolder(footerView);
        }else{
            return adapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(isHeader(position) || isFooter(position)){
            return;
        }
        int realPosition = position - 1;
        adapter.onBindViewHolder(holder, realPosition);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(isHeader(position)){
            return TYPE_HEADER;
        }else if(isFooter(position)){
            return TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    private boolean isHeader(int position){
        return position == 0;
    }

    private boolean isFooter(int position){
        return position == getItemCount() - 1;
    }

    public void addHeaderView(View view){
        headerView = view;
    }

    public void addFooterView(View view){
        footerView = view;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
