package com.pfl.common.http;

import java.util.concurrent.*;

/**
 * 线程池管理类
 */
public class ThreadPoolManager {

    private static volatile ThreadPoolManager instance;

    //线程队列 FIFO原则
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

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

}
