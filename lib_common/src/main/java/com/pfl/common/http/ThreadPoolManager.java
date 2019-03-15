package com.pfl.common.http;

import android.util.Log;

import java.util.concurrent.*;

import static com.pfl.common.constant.Constants.TAG;

/**
 * 线程池管理类
 */
public class ThreadPoolManager {

    private static volatile ThreadPoolManager instance;

    //线程队列 FIFO原则
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //延迟队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public static ThreadPoolManager getInstance() {

        if (null == instance) {
            synchronized (ThreadPoolManager.class) {
                if (null == instance) {
                    instance = new ThreadPoolManager();
                }
            }
        }

        return instance;
    }

    //将异步任务添加到对列
    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //将异步任务添加到对列
    public void addDelayTask(HttpTask runnable) {
        if (runnable != null) {

            if (runnable.getRetryCount() == 0) {
                runnable.setDelayTime(3000);//延迟3秒后重新执行
            } else if (runnable.getRetryCount() == 1) {
                runnable.setDelayTime(6000);//延迟6秒后重新执行
            } else if (runnable.getRetryCount() == 2) {
                runnable.setDelayTime(9000);//延迟9秒后重新执行
            }
            mDelayQueue.put(runnable);
        }
    }

    /**
     * 线程池 用来执行线程队列中的任务
     */
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //如果执行线程出错 从新添加到对列
                try {
                    mQueue.put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //启动核心线程
        mThreadPoolExecutor.execute(coreThread);
        //启动延迟重试线程
        mThreadPoolExecutor.execute(delayThread);
    }

    //创建核心线程 不停的获取对列中的任务 提交给线程池处理
    public Runnable coreThread = new Runnable() {
        @Override
        public void run() {

            Runnable runn;
            while (true) {
                try {
                    runn = mQueue.take();
                    mThreadPoolExecutor.execute(runn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };
    //创建核心线程 不停的获取对列中的任务 提交给线程池处理
    public Runnable delayThread = new Runnable() {
        @Override
        public void run() {

            HttpTask runn;
            while (true) {
                try {
                    runn = mDelayQueue.take();
                    if (runn.getRetryCount() < 3) {
                        mThreadPoolExecutor.execute(runn);
                        runn.setRetryCount(runn.getRetryCount() + 1);
                        Log.e(TAG, "第" + runn.getRetryCount() + "次执行失败");
                    } else {
                        Log.e(TAG, "执行失败 放弃治疗");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };

}
