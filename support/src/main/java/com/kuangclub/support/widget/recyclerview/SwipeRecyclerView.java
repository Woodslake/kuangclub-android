package com.kuangclub.support.widget.recyclerview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
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

    private OnScrollListener onScrollListener;

    boolean isScrolling = false;
    boolean loadingMoreEnabled = true;


    public SwipeRecyclerView(Context context) {
        super(context);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View defaultHeaderView = LayoutInflater.from(context).inflate(R.layout.header_default, null);
        View defaultFooterView = LayoutInflater.from(context).inflate(R.layout.footer_default, null);
        setHeaderView(defaultHeaderView);
        setFooterView(defaultFooterView);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        headerView.setVisibility(View.GONE);
        footerView.setVisibility(View.GONE);
        swipeAdapter = new SwipeAdapter(adapter);
        swipeAdapter.addHeaderView(headerView);
        swipeAdapter.addFooterView(footerView);
        super.setAdapter(swipeAdapter);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        Log.i("SwipeRecyclerView", "state: " + state);
        if(state == SCROLL_STATE_IDLE && isScrolling && loadingMoreEnabled){
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            int excludeFooterItemCount = layoutManager.getItemCount() - 1;
            if(
                    layoutManager.getChildCount() > 0
                    && excludeFooterItemCount >= layoutManager.getChildCount()
                    && lastVisibleItemPosition >= excludeFooterItemCount
            ){
                if(onScrollListener != null){
                    onScrollListener.onLoadMore();
                }
            }
        }
        isScrolling = state != SCROLL_STATE_IDLE;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setHeaderView(View view){
        headerView = view;
    }

    public void setFooterView(View view){
        footerView = view;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener){
        this.onScrollListener = onScrollListener;
    }

    public void refresh(){
        headerView.setVisibility(View.VISIBLE);
    }

    public void refreshOver(){
        headerView.setVisibility(View.GONE);
    }

    public void loadMore(){
        footerView.setVisibility(View.VISIBLE);
    }

    public void loadMoreOver(){
        footerView.setVisibility(View.GONE);
    }

    public interface OnScrollListener {

        void onRefresh();

        void onLoadMore();

    }

}
