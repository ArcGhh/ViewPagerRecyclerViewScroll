package com.arcghh.mylibrary.util;

import android.app.Application;

/**
 * @author ganhuanhui
 * @date 2020/10/13 0013
 * @desc
 */
public class LibSDK {

    private static Application mApplication;

    public static void init(Application application){
        mApplication = application;
        SizeUtils.init(mApplication);
    }

    public static Application getApplication() {
        return mApplication;
    }
}



