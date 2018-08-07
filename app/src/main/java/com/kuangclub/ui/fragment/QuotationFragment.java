package com.kuangclub.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuangclub.R;
import com.kuangclub.ui.adapter.HomeRecyclerAdapter;
import com.kuangclub.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Woodslake on 2018/7/27.
 */
public class QuotationFragment extends BaseFragment {
    private RecyclerView recyclerView;

    private HomeRecyclerAdapter homeRecyclerAdapter;

    private List<String> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fagment_quotation, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        list = new ArrayList<>();
        homeRecyclerAdapter = new HomeRecyclerAdapter(list);
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeRecyclerAdapter);

        refresh();
    }

    private void refresh(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            String str = "i = " + i;
            list.add(str);
        }
        this.list.clear();
        this.list.addAll(list);
        homeRecyclerAdapter.notifyDataSetChanged();
    }

}
