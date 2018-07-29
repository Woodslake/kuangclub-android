package com.kuangclub.support.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.kuangclub.support.R;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class SwipeRecyclerView extends RecyclerView {
    private AdapterWrapper adapterWrapper;
    private View headerView;
    private View footerView;

    private OnSwipeListener onSwipeListener;

    private int mTouchSlop;
    private int activePointerId;
    private float startY;
    boolean isScrolling;
    boolean isRefreshing;
    boolean isLoadingMore;

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        View defaultHeaderView = LayoutInflater.from(context).inflate(R.layout.header_default, null);
        View defaultFooterView = LayoutInflater.from(context).inflate(R.layout.footer_default, null);
        setHeaderView(defaultHeaderView);
        setFooterView(defaultFooterView);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        headerView.setVisibility(View.GONE);
        footerView.setVisibility(View.GONE);
        adapterWrapper = new AdapterWrapper(adapter);
        adapterWrapper.addHeaderView(headerView);
        adapterWrapper.addFooterView(footerView);
        super.setAdapter(adapterWrapper);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        Log.i("SwipeRecyclerView", "state: " + state);
        if(state == SCROLL_STATE_IDLE && isScrolling){
            if(!canScrollVertically(1)){
                if(onSwipeListener != null){
                    onSwipeListener.onLoadMore();
                }
            }
        }
        isScrolling = state != SCROLL_STATE_IDLE;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        final int action = e.getActionMasked();
        int pointerIndex;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                activePointerId = e.getPointerId(0);
                Log.i("SwipeRecyclerView", "activePointerId: " + activePointerId);
                pointerIndex = e.findPointerIndex(activePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                startY = e.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = e.findPointerIndex(activePointerId);
                if (pointerIndex < 0) {
                    Log.e("SwipeRecyclerView", "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                final float y = e.getY(pointerIndex);
                if(y - startY > mTouchSlop && !isRefreshing){
                    if(onSwipeListener != null){
                        onSwipeListener.onRefresh();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(e);
    }

    public void setHeaderView(View view){
        headerView = view;
    }

    public void setFooterView(View view){
        footerView = view;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener){
        this.onSwipeListener = onSwipeListener;
    }

    public void refresh(){
        Log.i("SwipeRecyclerView", "refresh");
        isRefreshing = true;
        headerView.setVisibility(View.VISIBLE);
    }

    public void refreshOver(){
        Log.i("SwipeRecyclerView", "refreshOver");
        isRefreshing = false;
        headerView.setVisibility(View.GONE);
    }

    public void loadMore(){
        Log.i("SwipeRecyclerView", "loadMore");
        isLoadingMore = true;
        footerView.setVisibility(View.VISIBLE);
    }

    public void loadMoreOver(){
        Log.i("SwipeRecyclerView", "loadMoreOver");
        isLoadingMore = false;
        footerView.setVisibility(View.GONE);
    }

    public interface OnSwipeListener {

        void onRefresh();

        void onLoadMore();

    }

}
