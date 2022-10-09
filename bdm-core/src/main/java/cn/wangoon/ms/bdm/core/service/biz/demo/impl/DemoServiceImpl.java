package cn.wangoon.ms.bdm.core.service.biz.demo.impl;

import cn.wangoon.ms.bdm.core.service.biz.demo.DemoService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Remark
 * @Author YINZHIYU
 * @Date 2022/10/1 3:04
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String demoInfo(String message) {
        return "我是一个Dubbo 形式的 DemoInfo" + message;
    }
}
