package cn.wangoon.ms.bdm.web.rpc.biz.demo;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.api.dto.DemoDto;
import cn.wangoon.ms.bdm.api.exception.DemoServiceException;
import cn.wangoon.ms.bdm.api.rpc.DemoServiceRpc;
import cn.wangoon.ms.bdm.core.service.biz.demo.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


/**
 * @Description DemoServiceRpcImpl
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-08 11:24:13
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Slf4j
@DubboService(version = "1.0.0", timeout = 1000 * 6000 * 5)
public class DemoServiceRpcImpl implements DemoServiceRpc {

    @Resource
    DemoService demoService;

    @Override
    public Result<String> demoInfo(DemoDto demoDto) throws DemoServiceException {
        return Result.success(demoService.demoInfo(demoDto.getMessage()));
    }
}