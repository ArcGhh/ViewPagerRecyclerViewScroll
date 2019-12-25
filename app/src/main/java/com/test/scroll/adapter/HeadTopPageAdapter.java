package com.test.scroll.adapter;


import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.scroll.R;
import com.test.scroll.bean.DataInfo;

/**
 * @author ganhuanhui
 * 时间：2019/12/2 0002
 * 描述：精选页 头部Recyclerview.GridView的adapter
 */
public class HeadTopPageAdapter extends BaseQuickAdapter<DataInfo, BaseViewHolder> {


    public HeadTopPageAdapter() {
        super(R.layout.item_head_top);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataInfo item) {
        helper.setText(R.id.tv_name, item.name);
        ImageView iv = helper.getView(R.id.iv);
        Glide.with(mContext)
                .load(item.img)
                .into(iv);
    }
}
