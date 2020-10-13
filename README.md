# ViewPagerRecyclerViewScroll
解决ViewPager嵌套Recyclerview滑动冲突

## 步骤
1.自定view继承RecyclerView 重写dispatchTouchEvent

2.监听手指移动方向 水平方向大于竖直方向 则禁止ViewPager的滑动 否则开启ViewPager的滑动

以上步骤就是整个功能的核心思想，也算是一个取巧吧！

##  核心代码
重写Recyclerview触摸事件

```
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //解决recyclerView和viewPager的滑动影响
        //当滑动recyclerView时，告知父控件不要拦截事件，交给子view处理
        get(false);
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
            //true 禁止ViewPager滑动，自动交给recyclerview去滑动
            //false 交给ViewPager滑动
            NoScrollViewPager viewPager = (NoScrollViewPager) mViewParent;
            viewPager.setNoScroll(isEnable);

        } else {
            get(isEnable);
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
