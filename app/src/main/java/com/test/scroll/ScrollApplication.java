package com.test.scroll;

import android.app.Application;

/**
 * @author ganhuanhui
 * 时间：2019/12/25 0025
 * 描述：
 */
public class ScrollApplication extends Application {

    private static ScrollApplication sApplication;

    public static ScrollApplication getApplication() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
