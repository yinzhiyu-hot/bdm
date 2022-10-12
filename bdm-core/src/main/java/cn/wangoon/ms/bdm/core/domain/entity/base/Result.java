package cn.wangoon.ms.bdm.core.domain.entity.base;

import cn.hutool.core.util.StrUtil;
import cn.wangoon.ms.bdm.core.common.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 响应结果类
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-12 11:39:02
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@ApiModel(description = "响应结果")
@Data
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "成功与否")
    private Boolean success;
    @ApiModelProperty(value = "返回码(成功标记=0，失败标记=1)")
    private Integer code;
    @ApiModelProperty(value = "错误信息")
    private String error;
    @ApiModelProperty(value = "业务信息")
    private String msg;
    @ApiModelProperty(value = "响应内容")
    private T datas;

    public Result() {
    }

    public Result(T datas) {
        this.datas = datas;
    }

    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.code = ResultEnum.SUCCESS.getCode();
        result.success = ResultEnum.SUCCESS.isSuccess();
        result.msg = ResultEnum.SUCCESS.getMessage();
        result.error = StrUtil.EMPTY;
        result.datas = null;
        return result;
    }

    public static <T> Result<T> ok(T datas) {
        Result<T> result = new Result<>();
        result.code = ResultEnum.SUCCESS.getCode();
        result.success = ResultEnum.SUCCESS.isSuccess();
        result.msg = ResultEnum.SUCCESS.getMessage();
        result.error = StrUtil.EMPTY;
        result.datas = datas;
        return result;
    }

    public static <T> Result<T> ok(String msg) {
        Result<T> result = new Result<>();
        result.code = ResultEnum.SUCCESS.getCode();
        result.success = ResultEnum.SUCCESS.isSuccess();
        result.msg = msg;
        result.error = StrUtil.EMPTY;
        result.datas = null;
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.code = ResultEnum.FAIL.getCode();
        result.success = ResultEnum.FAIL.isSuccess();
        result.msg = ResultEnum.FAIL.getMessage();
        result.error = ResultEnum.FAIL.getMessage();
        result.datas = null;
        return result;
    }

    public static <T> Result<T> fail(String fail) {
        Result<T> result = new Result<>();
        result.code = ResultEnum.FAIL.getCode();
        result.success = ResultEnum.FAIL.isSuccess();
        result.msg = ResultEnum.FAIL.getMessage();
        result.error = fail;
        result.datas = null;
        return result;
    }

    public static <T> Result<T> exception(String error) {
        Result<T> result = new Result<>();
        result.code = ResultEnum.EXCEPTION.getCode();
        result.success = ResultEnum.EXCEPTION.isSuccess();
        result.msg = ResultEnum.EXCEPTION.getMessage();
        result.error = error;
        result.datas = null;
        return result;
    }
}
