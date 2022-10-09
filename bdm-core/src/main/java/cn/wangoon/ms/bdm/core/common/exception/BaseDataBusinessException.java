package cn.wangoon.ms.bdm.core.common.exception;

import cn.wangoon.biz.common.exception.BusinessException;

/**
 * @Description 基础数据业务异常
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-26 15:56:57
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public class BaseDataBusinessException extends BusinessException {

    private static final long serialVersionUID = 8218356586534890879L;

    public BaseDataBusinessException() {
    }

    public BaseDataBusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}