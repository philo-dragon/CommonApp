package com.pfl.common.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class A {
    public static Executor diskIO = Executors.newSingleThreadExecutor();

    public static Executor networkIO = Executors.newFixedThreadPool(3);

    public static Executor mainThread = new MainThreadExecutor();

    static class MainThreadExecutor implements Executor {
        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }

}
