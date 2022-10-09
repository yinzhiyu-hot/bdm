package cn.wangoon.ms.bdm.web.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 应用初始化类，用于一些初始化，或加载Bean逻辑
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-26 16:39:29
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Configuration
@ComponentScan(basePackages = {"cn.wangoon.ms.bdm.*"})
public class ApplicationConfig implements InitializingBean, ApplicationContextAware {

    private static ApplicationContext ctx = null;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        ApplicationConfig.ctx = ctx;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //	初始化：

    }

    public static ApplicationContext getCtx() {
        return ctx;
    }

}
