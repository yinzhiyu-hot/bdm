package cn.wangoon.ms.bdm.job.handlers.base;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.api.dto.SysLogDto;
import cn.wangoon.ms.bdm.api.rpc.SysLogServiceRpc;
import cn.wangoon.ms.bdm.core.common.cache.impl.SysBaseConfigCache;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 基础配置Job
 * @Remark 包含多个调度句柄
 * @Author YINZHIYU
 * @Date 2022-09-27 19:50:57
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
public class SysBaseConfigJob {
    @Resource
    private SysBaseConfigCache sysBaseConfigCache;

    @DubboReference(version = "1.0.0", check = false, timeout = 1000 * 6000 * 5)
    private SysLogServiceRpc sysLogServiceRpc;

    /**
     * @Description 基础数据同步句柄
     * @Remark
     * @Params ==>
     * @Param null
     * @Return
     * @Date 2022-09-27 19:51:21
     * @Auther YINZHIYU
     */
    @XxlJob("sysBaseConfigSyncHandle")
    public void sysBaseConfigSyncHandle() {
        Result<SysLogDto> result = sysLogServiceRpc.selectByBusinessKey("aa");
        sysBaseConfigCache.init();
    }
}
