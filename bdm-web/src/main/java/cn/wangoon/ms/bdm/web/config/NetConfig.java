package cn.wangoon.ms.bdm.web.config;

import cn.wangoon.ms.bdm.core.common.constants.BdmConstants;
import cn.wangoon.ms.bdm.core.common.utils.NetUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @Description 本地Net配置
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-01 01:35:24
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Configuration
public class NetConfig {

    @Resource
    Environment environment;

    /**
     * @Description 获取Ip+端口
     * @Remark
     * @Params ==>
     * @Return java.lang.String
     * @Date 2022-10-01 01:35:30
     * @Auther YINZHIYU
     */
    public String getLocalIpPort() {
        return String.format("%s:%s", NetUtils.getLocalIP(), getLocalPort());
    }

    /**
     * @Description 获取应用端口
     * @Remark
     * @Params ==>
     * @Return java.lang.String
     * @Date 2022-10-01 01:35:37
     * @Auther YINZHIYU
     */
    public String getLocalPort() {
        return environment.getProperty(BdmConstants.LOCAL_SERVER_PORT);
    }
}