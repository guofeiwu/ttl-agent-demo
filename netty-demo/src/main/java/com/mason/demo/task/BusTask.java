package com.mason.demo.task;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.mason.demo.log.TraceChain;
import com.mason.demo.log.TraceHolder;
import com.mason.demo.server.ThreadPoolUtil2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 16:27
 * @since v1.0
 */
public class BusTask implements Runnable {


    Logger LOGGER = LoggerFactory.getLogger(BusTask.class);


    private TraceChain traceChain;

    private String message;

    public BusTask(TraceChain traceChain, String message) {
        this.traceChain = traceChain;
        this.message = message;
    }

    @Override
    public void run() {
        LOGGER.info("BusTask parentThread trace = {},current thread trace = {}, message = {}", traceChain.getTraceId(), TraceHolder.get().getTraceId(), message + PrintUtil.print(traceChain.getTraceId(), TraceHolder.get().getTraceId()));

        new Thread(() -> {

            LOGGER.info("BusTask parentThread trace = {},new thread trace = {}, message = {}", traceChain.getTraceId(), TraceHolder.get().getTraceId(), message + PrintUtil.print(traceChain.getTraceId(), TraceHolder.get().getTraceId()));

        }).start();
        // 特殊说明，只有第一次一次，后面均不一致，核心线程数为1，最大为2
        ThreadPoolUtil2.getThreadPoolExecutor().execute(() -> {
            LOGGER.info("BusTask parentThread trace = {},new pool thread trace = {}, message = {}", traceChain.getTraceId(), TraceHolder.get().getTraceId(), message + PrintUtil.print(traceChain.getTraceId(), TraceHolder.get().getTraceId()));
        });


        // 特殊说明，包装后请求多次均正常，核心线程数为1，最大为2
        TtlExecutors.getTtlExecutor(ThreadPoolUtil2.getThreadPoolExecutor()).execute(() -> {
            LOGGER.info("BusTask parentThread trace = {},wrapper new pool thread trace = {}, message = {}", traceChain.getTraceId(), TraceHolder.get().getTraceId(), message + PrintUtil.print(traceChain.getTraceId(), TraceHolder.get().getTraceId()));
        });


        CompletableFuture.runAsync(() -> {
            LOGGER.info("BusTask parentThread trace = {},CompletableFuture thread trace = {}, message = {}", traceChain.getTraceId(), TraceHolder.get().getTraceId(), message + PrintUtil.print(traceChain.getTraceId(), TraceHolder.get().getTraceId()));
        });

        CompletableFuture.runAsync(() -> {
            LOGGER.info("BusTask parentThread trace = {},CompletableFuture thread pool trace = {}, message = {}", traceChain.getTraceId(), TraceHolder.get().getTraceId(), message + PrintUtil.print(traceChain.getTraceId(), TraceHolder.get().getTraceId()));
        }, ThreadPoolUtil2.getThreadPoolExecutor());


        CompletableFuture.runAsync(() -> {
            LOGGER.info("BusTask parentThread trace = {},CompletableFuture wrapper thread pool trace = {}, message = {}", traceChain.getTraceId(), TraceHolder.get().getTraceId(), message + PrintUtil.print(traceChain.getTraceId(), TraceHolder.get().getTraceId()));
        }, TtlExecutors.getTtlExecutor(ThreadPoolUtil2.getThreadPoolExecutor()));

    }
}
