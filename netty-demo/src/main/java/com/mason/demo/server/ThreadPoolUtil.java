package com.mason.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 13:50
 * @since v1.0
 */
public class ThreadPoolUtil {

    static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolUtil.class);


    static MyThreadPoolExecutor threadPoolExecutor;

    public static MyThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPoolExecutor == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new MyThreadPoolExecutor(1,
                            2, 180,
                            100, "thread-name1-prefix");
                }
            }
        }
        return threadPoolExecutor;
    }


    public static void execute(Runnable runnable) {
        getThreadPoolExecutor().execute(runnable);
    }


}
