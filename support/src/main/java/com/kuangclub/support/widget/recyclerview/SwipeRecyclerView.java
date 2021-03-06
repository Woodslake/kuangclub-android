package com.kuangclub.support.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.kuangclub.support.R;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class SwipeRecyclerView extends RecyclerView {
    private static final float DRAG_RATE = 0.5f;

    private AdapterWrapper adapterWrapper;
    private View headerView;
    private View footerView;

    private OnSwipeListener onSwipeListener;

    private int headerViewWidth;
    private float totalDragDistance;
    private int touchSlop;
    private int activePointerId;
    private float startY;
    boolean isScrolling;
    boolean isRefreshing;
    boolean isLoadingMore;

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        totalDragDistance = 100 * metrics.density;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        headerView = LayoutInflater.from(context).inflate(R.layout.header_default, null);
        footerView = LayoutInflater.from(context).inflate(R.layout.footer_default, null);
        headerView.post(new Runnable() {
            @Override
            public void run() {
                headerViewWidth = headerView.getWidth();
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
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
                    loadMore();
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
                Log.i("SwipeRecyclerView", "pointerIndex: " + pointerIndex);
                if (pointerIndex < 0) {
                    Log.e("SwipeRecyclerView", "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                final float y = e.getY(pointerIndex);
                final float yDiff = y - startY;
                Log.i("SwipeRecyclerView", "canScrollVertically: " + canScrollVertically(-1) + ", yDiff: " + yDiff + ", touchSlop: " + touchSlop);
                if(!canScrollVertically(-1) && yDiff > touchSlop){
                    final float overScrollTop = yDiff * DRAG_RATE;
                    zoom(overScrollTop);
                }
                break;
            case MotionEvent.ACTION_UP:
                revert();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(e);
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener){
        this.onSwipeListener = onSwipeListener;
    }

    private void zoom(float overScrollTop){
        if(overScrollTop >= totalDragDistance){
            if(!isRefreshing){
                isRefreshing = true;
            }
        }else {
            isRefreshing = false;
        }
        float overWidth = Math.max(0, overScrollTop - totalDragDistance) * 0.5f;
        float overHeight = Math.max(overScrollTop, totalDragDistance);
        overWidth = overScrollTop * 0.5f;
        overHeight = overScrollTop;
        ViewGroup.LayoutParams layoutParams = headerView.getLayoutParams();
        layoutParams.height = (int) (overHeight);
        layoutParams.width = (int) (headerViewWidth + overWidth);
        ((MarginLayoutParams)layoutParams).setMargins(-(layoutParams.width - headerViewWidth) / 2, 0, 0, 0);
        headerView.setLayoutParams(layoutParams);
    }

    private void revert(){
        float overScrollTop = 0;
        if(isRefreshing){
            overScrollTop = totalDragDistance;
        }
        zoom(overScrollTop);
    }

    private void refresh(){
        Log.i("SwipeRecyclerView", "refresh");
        isRefreshing = true;
        if(onSwipeListener != null){
            onSwipeListener.onRefresh();
        }
    }

    public void refreshOver(){
        Log.i("SwipeRecyclerView", "refreshOver");
        isRefreshing = false;
    }

    private void loadMore(){
        Log.i("SwipeRecyclerView", "loadMore");
        isLoadingMore = true;
//        footerView.setVisibility(View.VISIBLE);
    }

    public void loadMoreOver(){
        Log.i("SwipeRecyclerView", "loadMoreOver");
        isLoadingMore = false;
//        footerView.setVisibility(View.GONE);
    }

    public interface OnSwipeListener {

        void onRefresh();

        void onLoadMore();

    }

}
