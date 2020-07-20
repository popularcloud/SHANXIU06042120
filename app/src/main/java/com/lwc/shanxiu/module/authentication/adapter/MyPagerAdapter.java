package com.lwc.shanxiu.module.authentication.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lwc.shanxiu.module.authentication.fragment.TopicFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private int NUM_PAGES = 100;
    private String parentId;

    public MyPagerAdapter(FragmentManager fm,String parentId) {
        super(fm);
        this.parentId = parentId;
    }
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("topicPosition",position);
        bundle.putString("parentId",parentId);
        TopicFragment toPicFragment = new TopicFragment();
        toPicFragment.setArguments(bundle);
        return toPicFragment;
      }
      @Override
      public CharSequence getPageTitle(int position) {
          return String.valueOf(position);
      }
      @Override
      public int getCount() {
          return NUM_PAGES;
      }
}