package com.arcghh.mylibrary.util;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import me.jessyan.autosize.utils.AutoSizeUtils;

public class SizeUtils {

    private SizeUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }
    
    private static Application mApplication;

    public static void init(Application application){
        mApplication = application;
    }

    public static int dp2px(float value) {
        return AutoSizeUtils.dp2px(mApplication, value);
    }

    public static int sp2px(float value) {
        return AutoSizeUtils.sp2px(mApplication, value);
    }

    public static int pt2px(float value) {
        return AutoSizeUtils.pt2px(mApplication, value);
    }

    public static int in2px(float value) {
        return AutoSizeUtils.in2px(mApplication, value);
    }

    public static int mm2px(float value) {
        return AutoSizeUtils.mm2px(mApplication, value);
    }

    public static float getScreenWidth() {
        WindowManager w = (WindowManager) mApplication.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);

        return metrics.widthPixels;
    }

    public static float getScreenHeight() {
        WindowManager w = (WindowManager) mApplication.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static float getStatusBarHeight(Context context) {
        float result = 24;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            result = AutoSizeUtils.dp2px(context, result);
        }
        return result;
    }
}
