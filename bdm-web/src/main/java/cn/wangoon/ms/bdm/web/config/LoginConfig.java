package cn.wangoon.ms.bdm.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description Ocs-Job 系统后台管理中心登录账户信息bean
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-01 01:33:02
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Data
@Component
@ConfigurationProperties(prefix = "bdm-web.login")
public class LoginConfig {

    public String domain;

    public boolean enable;

    public String username;

    public String password;

    public List<String> urls;
}
