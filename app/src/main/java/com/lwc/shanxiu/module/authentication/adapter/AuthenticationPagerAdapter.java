package com.lwc.shanxiu.module.authentication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;

public class AuthenticationPagerAdapter extends FragmentPagerAdapter {

    private String [] titles;
    HashMap<Integer, Fragment> fragmentHashMap;

    public AuthenticationPagerAdapter(FragmentManager fm, String [] list, HashMap<Integer, Fragment> fragmentHashMap) {
        super(fm);
        this.titles = list;
        this.fragmentHashMap = fragmentHashMap;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragmentHashMap == null?0:fragmentHashMap.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentHashMap.get(position);
    }
}
