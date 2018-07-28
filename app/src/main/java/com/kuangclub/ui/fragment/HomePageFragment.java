package com.kuangclub.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuangclub.R;
import com.kuangclub.support.widget.recyclerview.SwipeRecyclerView;
import com.kuangclub.ui.adapter.HomeRecyclerAdapter;
import com.kuangclub.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class HomePageFragment extends BaseFragment {
    private SwipeRecyclerView recyclerView;

    private HomeRecyclerAdapter homeRecyclerAdapter;

    private int type;
    private List<String> list;
    private int index = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fagment_home_page, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        list = refresh();
        homeRecyclerAdapter = new HomeRecyclerAdapter(list);
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeRecyclerAdapter);

    }

    public HomePageFragment setType(int type) {
        this.type = type;
        return this;
    }

    private List<String> refresh(){
        List<String> list = new ArrayList<>();
        int start = index;
        for (int i = start; i < start + 10; i++){
            String str = "i = " + i;
            list.add(str);
            index++;
        }
        return list;
    }

    private void loadMore(){

    }
}
