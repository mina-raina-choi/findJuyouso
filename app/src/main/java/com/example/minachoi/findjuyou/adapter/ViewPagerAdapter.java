package com.example.minachoi.findjuyou.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.minachoi.findjuyou.fragment.MapFragment;
import com.example.minachoi.findjuyou.fragment.ListFragment;

// where the Fragments are initialised
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if( position == 0)
            fragment = new ListFragment();
        else if (position == 1)
            fragment = new MapFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "리스트";
        else if (position == 1)
            title = "지도";
        return title;
    }
}
