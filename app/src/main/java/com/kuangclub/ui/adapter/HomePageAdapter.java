package com.kuangclub.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Woodslake on 2018/7/28.
 */
public class HomePageAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] titles;

    public HomePageAdapter(FragmentManager fm, Fragment[] fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null ? fragments[position] : null;
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.length : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles != null ? titles[position] : null;
    }
}
