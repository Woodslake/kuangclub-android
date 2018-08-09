package com.kuangclub.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kuangclub.model.bean.InfoType;

import java.util.List;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class InfoPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<InfoType> infoTypes;

    public InfoPageAdapter(FragmentManager fm, List<Fragment> fragments, List<InfoType> infoTypes) {
        super(fm);
        this.fragments = fragments;
        this.infoTypes = infoTypes;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null ? fragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return infoTypes != null ? infoTypes.get(position).getTitle() : null;
    }
}
