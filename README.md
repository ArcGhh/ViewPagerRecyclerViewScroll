# ViewPagerRecyclerViewScroll
解决ViewPager嵌套Recyclerview滑动冲突

## 步骤
1.自定view继承RecyclerView 重写dispatchTouchEvent

2.监听手指移动方向 水平方向大于竖直方向 则禁止ViewPager的滑动 否则开启ViewPager的滑动

以上步骤就是整个功能的核心思想，也算是一个取巧吧！

##  核心代码
重写Recyclerview触摸事件

```
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
```

//重写ViewPager onTouchEvent onInterceptTouchEvent

```
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
```

## 使用
把HorizontalRecyclerView当做正常的Recyclerview使用即可
好了，大功告成，目前已经用在项目中 暂无发现问题 如有发现问题的朋友 还请私聊告知

## 参考
[Android禁止滑动的NoScrollViewPager](https://blog.csdn.net/yilei0033/article/details/79444099)

[Android 网格分页布局](https://github.com/GcsSloop/pager-layoutmanager)

##### about
如果我的代码对你有帮助，请给我一个star
