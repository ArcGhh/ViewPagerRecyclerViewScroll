package com.arcghh.mylibrary.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author ganhuanhui
 * 时间：2019/12/17 0017
 * 描述：可禁止滑动的ViewPager
 */
public class NoScrollViewPager extends ViewPager {
    // 是否禁止 viewpager 左右滑动
    private boolean noScroll = false;

    public void setNoScroll(boolean noScroll){
        this.noScroll = noScroll;
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else{
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else{
            return super.onInterceptTouchEvent(arg0);
        }
    }

}
