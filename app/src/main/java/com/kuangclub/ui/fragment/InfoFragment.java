package com.kuangclub.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuangclub.R;
import com.kuangclub.http.ResponseBody;
import com.kuangclub.http.ServiceFactory;
import com.kuangclub.model.bean.InfoType;
import com.kuangclub.model.service.InfoService;
import com.kuangclub.ui.adapter.InfoPageAdapter;
import com.kuangclub.ui.base.BaseFragment;
import com.kuangclub.util.Logger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Woodslake on 2018/7/27.
 */
public class InfoFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private InfoPageAdapter infoPageAdapter;
    private List<Fragment> fragments;
    private List<InfoType> infoTypes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        infoTypes = new ArrayList<>();
        fragments = new ArrayList<>();
        infoPageAdapter = new InfoPageAdapter(getChildFragmentManager(), fragments, infoTypes);
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        viewPager.setAdapter(infoPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setText(infoTypes.get(i).getTitle());
        }
        refreshData();
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        ServiceFactory.createService(getActivity(), InfoService.class)
                .getInfoTypeList()
                .enqueue(new Callback<ResponseBody<List<InfoType>>>() {
                    @Override
                    public void onResponse(Call<ResponseBody<List<InfoType>>> call, Response<ResponseBody<List<InfoType>>> response) {
                        List<InfoType> data = response.body().getData();
                        infoTypes.clear();
                        infoTypes.addAll(data);
                        fragments.clear();
                        for (InfoType infoType : infoTypes){
                            fragments.add(new InfoPageFragment().setType(infoType.getType()));
                        }
                        infoPageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody<List<InfoType>>> call, Throwable t) {
                        Logger.log(TAG, t.getMessage());
                    }
                });
    }
}
