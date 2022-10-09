package cn.wangoon.ms.bdm.web.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description MybatisPlus配置类
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-01 01:33:14
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
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
     * @Date 2022-10-01 01:34:20
     * @Auther YINZHIYU
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
