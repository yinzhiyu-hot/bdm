package cn.wangoon.ms.bdm.core.common.enums;

import cn.wangoon.fd.log.api.ErrorCode;
import cn.wangoon.fd.log.api.ErrorCodeField;

/**
 * @Description 基础数据相关错误码
 * @Remark 2位顶级域 + 2为场景码(模块)，三位明操作细码, 一共七位
 * @Author YINZHIYU
 * @Date 2022-09-26 15:57:52
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@ErrorCode(platformId = "00", moduleId = "01", name = "订单模块异常码定义")
public enum BaseDataCoreError {

    UNKNOWN_ERROR,

    @ErrorCodeField(operationId = "001", operationName = "参数为空")
    NULL_PARAMS,

    @ErrorCodeField(operationId = "002", operationName = "参数错误")
    ERROR_PARAMS;

}