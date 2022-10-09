package cn.wangoon.ms.bdm.api.rpc;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.api.dto.DemoDto;
import cn.wangoon.ms.bdm.api.exception.DemoServiceException;

/**
 * @Description 微服务DemoServiceRpc接口定义层
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 20:00:09
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface DemoServiceRpc {

    /**
     * @Description 根据 demoDto 查询对应的 demoInfo对象
     * @Remark
     * @Params ==>
     * @Param demoDto
     * @Return cn.wangoon.biz.common.Result<java.lang.String>
     * @Date 2022-10-08 11:21:29
     * @Auther YINZHIYU
     */
    Result<String> demoInfo(DemoDto demoDto) throws DemoServiceException;
}
