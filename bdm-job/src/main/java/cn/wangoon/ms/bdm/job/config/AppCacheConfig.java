package cn.wangoon.ms.bdm.job.config;

import cn.wangoon.ms.bdm.core.common.cache.impl.SysBaseConfigCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @Description 系统启动中加载各种全局配置
 * @Remark
 * @PackagePath cn.wangoon.config.AppCacheConfig
 * @Author YINZHIYU
 * @Date 2021/3/12 11:56
 * @Version 1.0.0.0
 **/
@Component
@Slf4j
public class AppCacheConfig {

    @Resource
    public SysBaseConfigCache sysBaseConfigCache;

    @PostConstruct
    public void init() {
        log.info("系统启动中");

        //初始化基础配置
        sysBaseConfigCache.init();
    }

    @PreDestroy
    public void destroy() {
        log.info("系统运行结束");
    }
}
