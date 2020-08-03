package com.example.personalapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
//定义成员变量
    private Context mContext;
    private List<Fragment> fragments;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior, Context context, List<Fragment> fragments) {
        super(fm, behavior);
        this.fragments=fragments;
        this.mContext=context;
    }
//
    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);  //同上ViewPager需要的Fragment从这里获取，position为需要展示的位置
    }
//
    @Override
    public int getCount() {

        return fragments.size(); //需要展示Fragment的数量
    }

}
