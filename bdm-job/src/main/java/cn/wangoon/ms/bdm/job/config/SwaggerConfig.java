package cn.wangoon.ms.bdm.job.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 丝袜哥配置
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-01 01:51:55
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Configuration
@EnableSwagger2
//使用增强功能
@EnableSwaggerBootstrapUI
@Profile({"dev", "test"})// 设置 dev test 环境开启
public class SwaggerConfig {

    /**
     * 扫包根路径
     **/
    //默认业务代码扫包路径
    private final String DEFAULT_PACKAGE = "cn.wangoon.ms.bdm.web.controller.biz";
    private final String DEFAULT_GROUP_NAME = "业务API";
    //自定义扫包路径(我定义了一个系统级别代码)
    private final String SYSTEM_PACKAGE = "cn.wangoon.ms.bdm.web.controller.base";
    private final String SYSTEM_GROUP_NAME = "系统API";
    //标题
    private final String TITLE = "DBM 基础数据管理系统";
    //描述
    private final String DESCRIPTION = "BDM-JOB";
    //termsOfServiceUrl
    private final String TERMS_OF_SERVICE_URL = "http://localhost:8002/dbm-job/doc.html";
    private final String VERSION = "1.0";
    //消息体对象
    private final String RESP = "ResponseMsg";


    @Bean(value = "defaultApi")
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName(DEFAULT_GROUP_NAME).select().apis(RequestHandlerSelectors.basePackage(DEFAULT_PACKAGE)).paths(PathSelectors.any()).build();
        return buildDefaultApi(docket);
    }

    @Bean(value = "systemApi")
    public Docket systemApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName(SYSTEM_GROUP_NAME).select().apis(RequestHandlerSelectors.basePackage(SYSTEM_PACKAGE)).paths(PathSelectors.any()).build();
        return buildDefaultApi(docket);
    }

    /**
     * @Description 默认配置
     * @Remark
     * @Params ==>
     * @Param docket
     * @Return springfox.documentation.spring.web.plugins.Docket
     * @Date 2022-10-01 02:06:50
     * @Auther YINZHIYU
     */
    public Docket buildDefaultApi(Docket docket) {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef(RESP)).build());
        responseMessageList.add(new ResponseMessageBuilder().code(400).message("参数校验异常").responseModel(new ModelRef(RESP)).build());
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("没有登录认证/授权码过期").responseModel(new ModelRef(RESP)).build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef(RESP)).build());
        responseMessageList.add(new ResponseMessageBuilder().code(402).message("无效权限").responseModel(new ModelRef(RESP)).build());

        docket.apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, responseMessageList).globalResponseMessage(RequestMethod.POST, responseMessageList).globalResponseMessage(RequestMethod.PUT, responseMessageList).globalResponseMessage(RequestMethod.DELETE, responseMessageList).securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

    /**
     * @Description apiInfo
     * @Remark
     * @Params ==>
     * @Return springfox.documentation.service.ApiInfo
     * @Date 2022-10-01 02:06:34
     * @Auther YINZHIYU
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).termsOfServiceUrl(TERMS_OF_SERVICE_URL).version(VERSION).build();
    }
}