package com.pfl.common.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object ThreadPoolUtil {

    public val ExecutordiskIO = Executors.newSingleThreadExecutor()

    public val networkIO = Executors.newFixedThreadPool(3)

    public val mainThread = MainThreadExecutor()

    class MainThreadExecutor : Executor {
        val handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            handler.post(command)
        }
    }

}