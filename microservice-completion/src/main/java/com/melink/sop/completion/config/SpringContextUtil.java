package com.melink.sop.completion.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext act;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("server start");
        act = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return act.getBean(tClass);
    }

}
