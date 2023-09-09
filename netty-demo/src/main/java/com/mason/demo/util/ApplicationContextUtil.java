package com.mason.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 20:29
 * @since v1.0
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> tClass) {
        return getApplicationContext().getBean(tClass);
    }

    public static <T> T getBean(String name, Class<T> tClass) {
        return getApplicationContext().getBean(name, tClass);
    }

}
