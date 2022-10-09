package cn.wangoon.ms.bdm.api.rpc;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.api.dto.SysLogDto;
import cn.wangoon.ms.bdm.api.exception.SysLogServiceException;

/**
 * @Description 微服务SysLogServiceRpc接口定义层
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 20:00:09
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface SysLogServiceRpc {

    /**
     * @Description 根据 businessKey 查询对应的 SysLogDto对象
     * @Remark
     * @Params ==>
     * @Param businessKey
     * @Return cn.wangoon.biz.common.Result<cn.wangoon.ms.bdm.api.dto.SysLogDto>
     * @Date 2022-09-27 19:59:03
     * @Auther YINZHIYU
     */
    Result<SysLogDto> selectByBusinessKey(String businessKey) throws SysLogServiceException;
}
