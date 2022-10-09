package cn.wangoon.ms.bdm.job.handlers.base;

import cn.wangoon.ms.bdm.core.service.base.SysLogService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 日志表数据结转 Job
 * @Remark 普通业务处理
 * @Author YINZHIYU
 * @Date 2022-09-28 12:40:26
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
public class SysLogDataCarryForwardJob {

    @Resource
    private SysLogService sysLogService;

    @XxlJob("sysLogDataCarryForwardJobHandler")
    public void sysLogDataCarryForwardJobHandler() {
        sysLogService.dataCarryForward();
    }
}