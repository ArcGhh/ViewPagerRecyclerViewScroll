package com.test.scroll.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author ganhuanhui
 * 时间：2019/12/25 0025
 * 描述：
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public MyPagerAdapter(FragmentManager fm, Context context, List<Fragment> mFragments, List<String> mTitles) {
        super(fm);
        this.context = context;
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    /**
     * //此方法用来显示tab上的名字
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
