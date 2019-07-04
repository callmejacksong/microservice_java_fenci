package com.melink.open.api.spring;

import com.melink.open.api.util.SensitiveWordUtil;
import com.melink.open.api.util.TaobaoWhitelistUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext act;

    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;
    @Autowired
    private TaobaoWhitelistUtil taobaoWhitelistUtil;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("server start");
        act = applicationContext;
        sensitiveWordUtil.init();
        taobaoWhitelistUtil.init();
    }

    public static <T> T getBean(Class<T> tClass) {
        return act.getBean(tClass);
    }

}
