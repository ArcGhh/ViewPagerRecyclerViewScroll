package com.arcghh.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arcghh.mylibrary.R;
import com.arcghh.mylibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 页码指示器类，获得此类实例后，可通过{@link PageIndicatorView#initIndicator(int)}方法初始化指示器
 * </P>
 */
public class PageIndicatorView extends LinearLayout {

    private int count = 0;
    private Context mContext = null;
    private int viewWidth, viewHeight = 0; // 指示器的大小（dp）
    private int margins = 4; // 指示器间距（dp）
    private List<ImageView> indicatorViews = null; // 存放指示器
    private int selectImg = android.R.drawable.presence_online, unSelectImg = android.R.drawable.presence_invisible;

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    public void init(AttributeSet attrs) {

        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        if (attrs != null) {
            final TypedArray a = mContext.obtainStyledAttributes(
                    attrs, R.styleable.PageIndicatorView);

            selectImg = a.getResourceId(R.styleable.PageIndicatorView_selectImg, android.R.drawable.presence_online);
            unSelectImg = a.getResourceId(R.styleable.PageIndicatorView_unSelectImg, android.R.drawable.presence_invisible);
            viewWidth = a.getInt(R.styleable.PageIndicatorView_imgWidth, 0);
            viewHeight = a.getInt(R.styleable.PageIndicatorView_imgHeight, 0);
            margins = a.getInt(R.styleable.PageIndicatorView_margins, 0);
            a.recycle();

        }

        this.viewWidth = SizeUtils.dp2px(viewWidth);
        this.viewHeight = SizeUtils.dp2px(viewHeight);
        this.margins = SizeUtils.dp2px(margins);
    }

    /**
     * 初始化指示器，默认选中第一页
     *
     * @param count 指示器数量，即页数
     */
    public void initIndicator(int count) {
        if (this.count == count) return;
        this.count = count;
        if (indicatorViews == null) {
            indicatorViews = new ArrayList<>();
        } else {
            indicatorViews.clear();
            removeAllViews();
        }
        ImageView view;
        LayoutParams params = new LayoutParams(viewWidth == 0 ? WRAP_CONTENT : viewWidth, viewHeight == 0 ? WRAP_CONTENT : viewHeight);
        params.setMargins(margins, margins, margins, margins);
        for (int i = 0; i < count; i++) {
            view = new ImageView(mContext);
            view.setImageResource(unSelectImg);
            addView(view, params);
            indicatorViews.add(view);
        }
        if (indicatorViews.size() > 0) {
            indicatorViews.get(0).setImageResource(selectImg);
        }
    }

    /**
     * 设置选中页
     *
     * @param selected 页下标，从0开始
     */
    public void setSelectedPage(int selected) {
        if (indicatorViews == null) return;
        for (int i = 0; i < indicatorViews.size(); i++) {
            if (i == selected) {
                indicatorViews.get(i).setImageResource(selectImg);
            } else {
                indicatorViews.get(i).setImageResource(unSelectImg);
            }
        }
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = SizeUtils.dp2px(viewWidth);
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = SizeUtils.dp2px(viewHeight);
    }

    public void setMargins(int margins) {
        this.margins = SizeUtils.dp2px(margins);
    }

    public void setSelectImg(int selectImg) {
        this.selectImg = selectImg;
    }

    public void setUnSelectImg(int unSelectImg) {
        this.unSelectImg = unSelectImg;
    }

    public int getCount() {
        return count;
    }
}