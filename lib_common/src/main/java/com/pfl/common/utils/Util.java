package com.pfl.common.utils;

import android.app.Application;

/**
 * 主要是提供Application 供给其他类使用
 * 在Application中做init 操作
 */
public class Util {

    private static volatile Util instance;

    private static Application mApplication;

    private Util() {
        if (null != instance) {
            throw new RuntimeException(this.getClass().getSimpleName() + " already existed");
        }
    }

    public static Util getInstance() {
        if (null == instance) {
            synchronized (Util.class) {
                if (null == instance) {
                    instance = new Util();
                }
            }
        }

        return instance;
    }

    public void init(Application application) {
        this.mApplication = application;
    }

    public Application getApplication() {
        if (null == mApplication) {
            throw new RuntimeException("please call init method in your app");
        }
        return mApplication;
    }


}
