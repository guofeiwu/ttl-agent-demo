package com.mason.demo.server;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor2 extends ThreadPoolExecutor {

    public MyThreadPoolExecutor2(int corePoolSize,
                                 int maximumPoolSize,
                                 long keepAliveTime,
                                 int queueCapacity,
                                 String threadNamePrefix) {
        super(corePoolSize,
        maximumPoolSize,
        keepAliveTime,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(queueCapacity),
        new DefaultThreadFactory(threadNamePrefix),
        new DiscardOldestPolicy());
    }
}