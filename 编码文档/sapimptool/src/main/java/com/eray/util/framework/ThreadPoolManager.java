package com.eray.util.framework;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private static final int CORE_POOL_SIZE = 30;
    private static final int MAX_POOL_SIZE = 80;
    private static final int KEEPALIVE_TIME = 12;
    private static final int QUEUE_CAPACITY = 1000;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private static ThreadPoolExecutor threadPoolExecutor;

    /**
     * 初始化
     * 
     * @return
     */
    public static void initThreadPool() {
        if (threadPoolExecutor == null) {
            synchronized (ThreadPoolManager.class) {
                if (threadPoolExecutor == null) {

                    BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY);
                    RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

                    threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEPALIVE_TIME,
                            TIME_UNIT, workQueue, rejectedExecutionHandler);
                }
            }
        }
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPoolExecutor == null) {
            initThreadPool();
        }
        return threadPoolExecutor;
    }

}
