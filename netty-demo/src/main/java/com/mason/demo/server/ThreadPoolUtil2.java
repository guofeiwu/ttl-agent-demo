package com.mason.demo.server;

import com.alibaba.ttl.TtlRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 13:50
 * @since v1.0
 */
public class ThreadPoolUtil2 {

    static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolUtil2.class);


    static MyThreadPoolExecutor2 threadPoolExecutor;

    public static MyThreadPoolExecutor2 getThreadPoolExecutor(){
        if(threadPoolExecutor == null){
            synchronized (ThreadPoolUtil2.class){
                if(threadPoolExecutor == null)
                {
                    threadPoolExecutor = new MyThreadPoolExecutor2(1,
                            2,180,
                            100,"thread-name2-prefix");
                }
            }
        }
        return threadPoolExecutor;
    }




    public static void execute(Runnable runnable){
        LOGGER.info("ThreadPoolUtil execute...");
        TtlRunnable ttlRunnable = TtlRunnable.get(runnable);
        getThreadPoolExecutor().execute(ttlRunnable);
    }




}
