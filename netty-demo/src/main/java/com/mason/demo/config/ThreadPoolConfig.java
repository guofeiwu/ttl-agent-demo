package com.mason.demo.config;

import com.mason.demo.server.MyThreadPoolExecutor2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 20:34
 * @since v1.0
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "contextThreadPool")
    public MyThreadPoolExecutor2 contextThreadPool() {
        return new MyThreadPoolExecutor2(1,
                2, 180,
                100, "thread-name-context-prefix");
    }
}
