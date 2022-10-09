package cn.wangoon.ms.bdm.web.config;

import cn.wangoon.ms.bdm.web.interceptor.LoginContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description WEB相关配置
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-01 01:36:20
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Configuration
public class WebMvcAutoConfig implements WebMvcConfigurer {

    /**
     * @Description 拦截器组
     * @Remark
     * @Params ==>
     * @Param registry
     * @Return void
     * @Date 2022-10-01 01:36:27
     * @Auther YINZHIYU
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ApplicationConfig.getCtx().getBean(LoginContextInterceptor.class));//bdm-job系统后台管理中心登录拦截器，线上环境访问需登录
    }

    /**
     * @Description 设置资源文件目录
     * @Remark
     * @Params ==>
     * @Param registry
     * @Return void
     * @Date 2022-10-01 01:36:40
     * @Auther YINZHIYU
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/").addResourceLocations("classpath:/static/**").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("swagger-ui.html", "doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * @Description 配置跨域
     * @Remark
     * @Params ==>
     * @Param registry
     * @Return void
     * @Date 2022-10-01 01:36:49
     * @Auther YINZHIYU
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 设置允许跨域的路径
                .allowedOrigins("*")// 设置允许跨域请求的域名
                .allowCredentials(true)// 是否允许证书 不再默认开启
                .allowedHeaders("*")//设置允许的请求头
                .allowedMethods("*")// 设置允许的方法
                .maxAge(3600);// 跨域允许时间
    }
}