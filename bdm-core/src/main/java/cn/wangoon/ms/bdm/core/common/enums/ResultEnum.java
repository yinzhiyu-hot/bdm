package cn.wangoon.ms.bdm.core.common.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Objects;

/**
 * @Description 结果标识
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 14:13:43
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public enum ResultEnum {
    SUCCESS("处理成功", 0, true),
    FAIL("处理失败", 1, false),
    EXCEPTION("处理异常", 99, false);

    private Integer code;

    private boolean success;

    private String message;

    // 构造方法
    ResultEnum(String message, Integer code, boolean success) {
        this.message = message;
        this.code = code;
        this.success = success;
    }

    public static String getMessage(Integer code) {
        for (ResultEnum codeEnum : ResultEnum.values()) {
            if (Objects.equals(codeEnum.getCode(), code)) {
                return codeEnum.getMessage();
            }
        }
        return StrUtil.EMPTY;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
