package com.pfl.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.pfl.common.BuildConfig
import com.pfl.common.netstate.NetworkManager
import com.pfl.common.utils.Util


open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Util.getInstance().init(this)
        NetworkManager.getInstance().init()
        initARouter()
    }

    /**
     * 初始化路由
     */
    private fun initARouter() {
        // 初始化 ARouter
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

}