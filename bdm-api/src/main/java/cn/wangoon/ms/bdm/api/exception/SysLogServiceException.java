package cn.wangoon.ms.bdm.api.exception;

import cn.wangoon.biz.common.exception.RPCException;

/**
 * @Description SysLogService远程服务异常定义, 具体抛出此异常的原因
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 19:57:37
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public class SysLogServiceException extends RPCException {

    private static final long serialVersionUID = -6289385470244252424L;

    public SysLogServiceException() {
    }

    public SysLogServiceException(String code, String message) {
        super(code, message);
    }

}
