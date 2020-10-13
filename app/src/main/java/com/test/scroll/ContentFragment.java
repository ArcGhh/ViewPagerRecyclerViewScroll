package com.test.scroll;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.test.scroll.adapter.DataAdapter;
import com.test.scroll.adapter.HeadTopPageAdapter;
import com.test.scroll.bean.DataInfo;
import com.test.scroll.databinding.FragmentContentBinding;
import com.test.scroll.util.SizeUtils;
import com.test.scroll.widget.HorizontalRecyclerView;
import com.test.scroll.widget.PageIndicatorView;
import com.test.scroll.widget.PagerConfig;
import com.test.scroll.widget.PagerGridLayoutManager;
import com.test.scroll.widget.PagerGridSnapHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ganhuanhui
 * 时间：2019/12/25 0025
 * 描述：
 */
public class ContentFragment extends Fragment {

    private int type = 0;
    private FragmentContentBinding mBinding;
    private DataAdapter mAdapter;

    private List<DataInfo> mHeadDatas = new ArrayList<>();
    private List<DataInfo> mDatas = new ArrayList<>();

    public static ContentFragment getInstance(int type) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        if (type % 2 == 0){
            initPagerGridLayout();
        }
    }

    private void initView() {
        mAdapter = new DataAdapter(mDatas);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                //这里需要return spanSize
                //格数=spanCount/spanSize,这里设置了spanCount=3
                switch (itemViewType) {
                    case DataInfo.TYPE_H:
                        return 1; // 3/1 = 3格
                    case DataInfo.TYPE_V:
                        return 3; // 3/3 = 1格
                    default:
                        return 3;
                }
            }
        });

        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "position: " + position, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initData() {
        type = getArguments().getInt("type",0);
        mHeadDatas.clear();
        mDatas.clear();
        for (int i = 0; i < 32; i++) {
            DataInfo info = new DataInfo();
            info.id = i + "";
            info.name = "hello " + i;
            info.img = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg";

            mHeadDatas.add(info);

            info.type = i % 7 == 0 ? DataInfo.TYPE_V : DataInfo.TYPE_H;
            mDatas.add(info);
        }
    }


    //网格banner
    // 文档地址： https://github.com/GcsSloop/pager-layoutmanager
    public void initPagerGridLayout() {
        removeHeadView(mAdapter);
        View view = getLayoutInflater().inflate(R.layout.layout_page_recyclerview, null);
        final HorizontalRecyclerView pageRecyclerView = view.findViewById(R.id.layoutRecyclerview);
        final PageIndicatorView pageIndicatorView = view.findViewById(R.id.pageIndicatorView);

        // 设置右边距
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pageRecyclerView.getLayoutParams();
        lp.rightMargin = type == 0 ? SizeUtils.dp2px(50) : 0;

        PagerGridLayoutManager mLayoutManager = new PagerGridLayoutManager(2, 4, PagerGridLayoutManager
                .HORIZONTAL);
        mLayoutManager.setAllowContinuousScroll(false);
        int fling = SizeUtils.dp2px(50);
        PagerConfig.setFlingThreshold(fling);
        pageRecyclerView.setLayoutManager(mLayoutManager);

        // 设置滚动辅助工具
        PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
        pageSnapHelper.attachToRecyclerView(pageRecyclerView);

        // 如果需要查看调试日志可以设置为true，一般情况忽略即可
        PagerConfig.setShowLog(true);

        // 使用原生的 Adapter 即可
        HeadTopPageAdapter topPageAdapter = new HeadTopPageAdapter();
        topPageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "position: " + position, Toast.LENGTH_LONG).show();
            }
        });

        topPageAdapter.setNewData(mHeadDatas);
        pageRecyclerView.setAdapter(topPageAdapter);

        mAdapter.addHeaderView(view);

        // 水平分页布局管理器
        mLayoutManager.setPageListener(new PagerGridLayoutManager.PageListener() {
            @Override
            public void onPageSizeChanged(final int pageSize) {
                if (mAdapter == null) return;
                pageIndicatorView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter == null) return;
                        pageIndicatorView.initIndicator(pageSize);
                        mAdapter.notifyDataSetChanged();
                        pageRecyclerView.setSize(pageSize);
                    }
                }, 200);
            }

            @Override
            public void onPageSelect(int pageIndex) {
                pageIndicatorView.setSelectedPage(pageIndex);
                pageRecyclerView.setPosition(pageIndex);
            }
        });

    }

    private void removeHeadView(BaseQuickAdapter baseQuickAdapter) {
        if (baseQuickAdapter.getHeaderLayoutCount() > 0) {
            baseQuickAdapter.getHeaderLayout().removeAllViews();
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatas.clear();
        mHeadDatas.clear();
        mAdapter = null;
        mBinding.unbind();
    }
}
