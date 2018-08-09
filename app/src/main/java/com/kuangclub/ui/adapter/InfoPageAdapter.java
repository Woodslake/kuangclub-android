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
    private List<Fragment> fragments;
    private List<Info> infos;

    public InfoPageAdapter(FragmentManager fm, List<Fragment> fragments, List<Info> infos) {
        super(fm);
        this.fragments = fragments;
        this.infos = infos;
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
        return infos != null ? infos.get(position).getTitle() : null;
    }
}
