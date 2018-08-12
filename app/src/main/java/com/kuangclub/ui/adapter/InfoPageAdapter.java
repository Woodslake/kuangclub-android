package com.kuangclub.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kuangclub.model.bean.Info;

import java.util.List;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class InfoPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<Info> infoList;

    public InfoPageAdapter(FragmentManager fm, List<Fragment> fragmentList, List<Info> infoList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.infoList = infoList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList != null ? fragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragmentList != null ? fragmentList.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return infoList != null ? infoList.get(position).getTitle() : null;
    }
}
