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
import com.kuangclub.ui.adapter.InfoPageAdapter;
import com.kuangclub.ui.base.BaseFragment;

/**
 * Created by Woodslake on 2018/7/27.
 */
public class InfoFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private InfoPageAdapter infoPageAdapter;
    private Fragment[] fragments;
    private String[] titles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fagment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        titles = getResources().getStringArray(R.array.home_tabs);
        fragments = new InfoPageFragment[titles.length];
        for (int i = 0; i < titles.length; i++){
            fragments[i] = new InfoPageFragment().setType(i);
        }
        infoPageAdapter = new InfoPageAdapter(getChildFragmentManager(), fragments, titles);
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        viewPager.setAdapter(infoPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setText(titles[i]);
        }
    }

}
