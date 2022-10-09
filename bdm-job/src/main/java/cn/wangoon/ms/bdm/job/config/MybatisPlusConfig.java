package cn.wangoon.ms.bdm.job.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description MybatisPlus配置类
 * @PackagePath com.example.dao.config.MybatisPlusConfig
 * @Author YINZHIYU
 * @Date 2020-04-07 09:37:00
 * @Version 1.0.0.0
 **/
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"cn.wangoon.ms.bdm.core.dao.mapper*"})
public class MybatisPlusConfig {

    /**
     * @Description 分页插件
     * @Remark
     * @Params ==>
     * @Return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     * @Date 2021-05-11 14:33:22
     * @Auther YINZHIYU
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
