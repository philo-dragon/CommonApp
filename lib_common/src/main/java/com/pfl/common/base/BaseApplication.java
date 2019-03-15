package com.pfl.common.base;

import android.app.Application;
import com.pfl.common.netstate.NetworkManager;
import com.pfl.common.utils.Util;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Util.getInstance().init(this);
        NetworkManager.getInstance().init();
    }
}
