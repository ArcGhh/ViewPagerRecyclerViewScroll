package com.test.scroll.adapter;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.scroll.R;
import com.test.scroll.bean.DataInfo;

import java.util.List;

/**
 * @author ganhuanhui
 * 时间：2019/12/25 0025
 * 描述：
 */
public class DataAdapter extends BaseMultiItemQuickAdapter<DataInfo, BaseViewHolder> {

    public DataAdapter(List<DataInfo> data) {
        super(data);
        addItemType(DataInfo.TYPE_H, R.layout.item_h);
        addItemType(DataInfo.TYPE_V, R.layout.item_v);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataInfo item) {

        switch (item.type) {
            case DataInfo.TYPE_H:
                bindH(helper, item);
                break;
            case DataInfo.TYPE_V:
                bindV(helper, item);
                break;
        }

    }

    private void bindH(BaseViewHolder helper, DataInfo item) {
        helper.setText(R.id.tv_name, item.name);
        ImageView ivIcon = helper.getView(R.id.iv);
        Glide.with(mContext).load(item.img).into(ivIcon);
    }

    private void bindV(BaseViewHolder helper, DataInfo item) {
        helper.setText(R.id.tv_name, item.name);

    }
}
