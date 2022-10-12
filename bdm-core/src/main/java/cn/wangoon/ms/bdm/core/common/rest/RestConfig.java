package cn.wangoon.ms.bdm.core.common.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description Rest 配置
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-09 17:42:53
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
public class RestConfig {
    @Value("${rest.wms.domain:'http://172.16.3.250/'}")
    public String WmsApiUrl;
}
