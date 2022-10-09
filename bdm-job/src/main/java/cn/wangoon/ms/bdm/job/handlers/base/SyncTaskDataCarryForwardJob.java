package cn.wangoon.ms.bdm.job.handlers.base;

import cn.wangoon.ms.bdm.core.service.base.SyncTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 任务表数据结转 Job
 * @Remark 普通业务处理
 * @Author YINZHIYU
 * @Date 2022-09-28 12:40:40
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
public class SyncTaskDataCarryForwardJob {

    @Resource
    private SyncTaskService syncTaskService;

    @XxlJob("syncTaskDataCarryForwardJobHandler")
    public void syncTaskDataCarryForwardJobHandler() {
        syncTaskService.dataCarryForward();
    }
}