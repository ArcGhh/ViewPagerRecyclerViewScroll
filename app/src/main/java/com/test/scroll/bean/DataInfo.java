package com.test.scroll.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author ganhuanhui
 * 时间：2019/12/25 0025
 * 描述：
 */
public class DataInfo implements MultiItemEntity {

    public static final int TYPE_H = 0;
    public static final int TYPE_V = 1;

    public String id;
    public String name;
    public String img;
    public int type;

    @Override
    public int getItemType() {
        return type;
    }
}
