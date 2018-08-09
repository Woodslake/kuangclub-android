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
import com.kuangclub.http.ResponseBody;
import com.kuangclub.http.ServiceFactory;
import com.kuangclub.model.bean.Info;
import com.kuangclub.model.service.InfoService;
import com.kuangclub.ui.adapter.InfoRecyclerAdapter;
import com.kuangclub.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class InfoPageFragment extends BaseFragment {
    private RecyclerView recyclerView;

    private InfoRecyclerAdapter infoRecyclerAdapter;

    private String type;
    private int page;
    private List<Info> infoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_page, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        infoList = new ArrayList<>();
        infoRecyclerAdapter = new InfoRecyclerAdapter(infoList);
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(infoRecyclerAdapter);

        refreshData();
    }

    public InfoPageFragment setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        page = 0;
        ServiceFactory.createService(getActivity(), InfoService.class)
                .getInfoList(type, page)
                .enqueue(new Callback<ResponseBody<List<Info>>>() {
                    @Override
                    public void onResponse(Call<ResponseBody<List<Info>>> call, Response<ResponseBody<List<Info>>> response) {
                        List<Info> data = response.body().getData();
                        infoList.clear();
                        infoList.addAll(data);
                        infoRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody<List<Info>>> call, Throwable t) {

                    }
                });
    }
}
