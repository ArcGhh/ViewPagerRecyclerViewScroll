package com.test.scroll;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.test.scroll.adapter.MyPagerAdapter;
import com.test.scroll.bean.DataInfo;
import com.test.scroll.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DataInfo> mDataInfos = new ArrayList<>();
    private ActivityMainBinding mBinding;
    private List<String> mTitles;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        mBinding.viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),this,mFragments,mTitles));
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("首页");
        mTitles.add("精选");
        mTitles.add("推荐");
        mTitles.add("我的");

        mFragments = new ArrayList<>();
        mFragments.add(ContentFragment.getInstance(0));
        mFragments.add(ContentFragment.getInstance(1));
        mFragments.add(ContentFragment.getInstance(2));
        mFragments.add(ContentFragment.getInstance(3));

    }
}
