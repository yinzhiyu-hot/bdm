package cn.wangoon.ms.bdm.job.handlers.biz.demo;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.api.dto.DemoDto;
import cn.wangoon.ms.bdm.api.rpc.DemoServiceRpc;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskTypeEnum;
import cn.wangoon.ms.bdm.core.service.base.SyncTaskService;
import cn.wangoon.ms.bdm.job.handlers.BaseSimpleJob;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 基于任务表的基础JOB示例
 * @Remark
 * @Author YINZHIYU
 * @Date 2022/9/28 12:42
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
public class DemoJob extends BaseSimpleJob {
    @Resource
    private SyncTaskService syncTaskService;

    @DubboReference(version = "1.0.0", check = false, timeout = 1000 * 6000 * 5)
    private DemoServiceRpc demoServiceRpc;


    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        String taskType = SyncTaskTypeEnum.DEMO_DO.getSyncTaskType();

        DemoDto demoDto = new DemoDto();
        demoDto.setMessage("我是Dubbo 消费者");

        Result<String> result = demoServiceRpc.demoInfo(demoDto);

        super.startExecuteJob(taskType);
    }
}
