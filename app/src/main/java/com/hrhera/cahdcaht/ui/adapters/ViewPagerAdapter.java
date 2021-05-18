package com.hrhera.cahdcaht.ui.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    private List<Fragment> list = new ArrayList<>();

    public void setFragmentsList(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    private List<String> titles = new ArrayList<>();

    public void setTitles(List<String > titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size()  ;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
