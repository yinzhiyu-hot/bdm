package cn.wangoon.ms.bdm.web.rpc.base;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.api.dto.SysLogDto;
import cn.wangoon.ms.bdm.api.exception.SysLogServiceException;
import cn.wangoon.ms.bdm.api.rpc.SysLogServiceRpc;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import cn.wangoon.ms.bdm.core.service.base.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


/**
 * @Description SysLogServiceRpcImpl
 * @Remark 注意这里是Dubbo注解： org.apache.dubbo.config.annotation.Service注解
 * @Author YINZHIYU
 * @Date 2022-09-27 20:06:38
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Slf4j
@DubboService(version = "1.0.0", timeout = 1000 * 6000 * 5)
public class SysLogServiceRpcImpl implements SysLogServiceRpc {

    @Resource
    private SysLogService sysLogService;

    /**
     * @Description 对外提供dubbo服务
     * @Remark
     * @Params ==>
     * @Param businessKey
     * @Return cn.wangoon.biz.common.Result<cn.wangoon.ms.bdm.api.dto.SysLogDto>
     * @Date 2022-09-27 20:06:09
     * @Auther YINZHIYU
     */
    @Override
    public Result<SysLogDto> selectByBusinessKey(String businessKey) throws SysLogServiceException {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysLog.COL_BUSINESS_KEY, businessKey);
        SysLog sysLog = sysLogService.getOne(queryWrapper);
        SysLogDto sysLogDto = SysLogDto.builder().businessKey(sysLog.getBusinessKey()).build();

        return Result.success(sysLogDto);
    }


}
