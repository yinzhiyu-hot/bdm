package cn.wangoon.ms.bdm.core.common.exception;

import cn.wangoon.biz.common.exception.ValidationException;

/**
 * @Description 基础数据校验异常
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-26 15:57:18
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public class BaseDataValidationException extends ValidationException {

    private static final long serialVersionUID = 8218356586534890879L;

    public BaseDataValidationException() {
    }

    public BaseDataValidationException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}