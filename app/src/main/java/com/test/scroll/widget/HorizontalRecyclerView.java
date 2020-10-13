package com.test.scroll.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;


/**
 * @author ganhuanhui
 * 时间：2019/12/2 0002
 * 描述：当外部有viewapger时 解决之间的滑动冲突
 */
public class HorizontalRecyclerView extends RecyclerView {

    private float x1;
    private float y1;

    /**
     * 之所以定义这2个页码是因为一下2种情况
     * 1. 当页码为 0 时，这个时候用户向左移动时，ViewPager是需要响应滑动的
     * 2. 当页码为 size - 1（最后一页） 这个时候用户向右移动时，ViewPager是需要响应滑动的
     */
    private int position = 0; // 当前页码
    private int size = 0; // 总页码

    public HorizontalRecyclerView(Context context) {
        super(context);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //解决recyclerView和viewPager的滑动影响
        //当滑动recyclerView时，告知父控件不要拦截事件，交给子view处理
        get(false);
        if (size == 1) {
            // 只有一条数据时，不做任何处理
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当手指按下的时候
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //当手指移动的时候
                float x2 = event.getX();
                float y2 = event.getY();
                if (position == 0 && x2 >= x1){
                    // 当前为第一页并且向左移动时，不做处理
                    return super.dispatchTouchEvent(event);
                }
                if (position == size - 1 && x2 <= x1){
                    // 当前为最后一页并且向右移动时，不做处理
                    return super.dispatchTouchEvent(event);
                }

                float offsetX = Math.abs(x2 - x1);
                float offsetY = Math.abs(y2 - y1);
                if (offsetX >= offsetY) {
                    get(true);//手指左移
                } else {
                    get(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                x1 = y1 = 0;
                get(false);
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private ViewParent mViewParent;

    //使用迭代 直至找到parent是NoScrollViewPager为止
    //效率有些低 偏low 莫见怪
    private void get(boolean isEnable) {
        if (mViewParent == null)
            mViewParent = getParent();
        else
            mViewParent = mViewParent.getParent();
        if (mViewParent instanceof NoScrollViewPager) {

            NoScrollViewPager viewPager = (NoScrollViewPager) mViewParent;
            viewPager.setNoScroll(isEnable);

        } else {
            get(isEnable);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
