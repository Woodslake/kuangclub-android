package com.kuangclub.support.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.kuangclub.support.R;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class SwipeRecyclerView extends RecyclerView {
    private SwipeAdapter swipeAdapter;
    private View headerView;
    private View footerView;

    public SwipeRecyclerView(Context context) {
        super(context);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        headerView = LayoutInflater.from(context).inflate(R.layout.header_default, null);
        footerView = LayoutInflater.from(context).inflate(R.layout.footer_default, null);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        swipeAdapter = new SwipeAdapter(adapter);
        super.setAdapter(swipeAdapter);
    }

    public void addHeaderView(View view){
        swipeAdapter.addHeaderView(view);
    }

    public void addFooterView(View view){
        swipeAdapter.addFooterView(view);
    }
}
