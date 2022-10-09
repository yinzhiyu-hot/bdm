package cn.wangoon.ms.bdm.web.config;

import cn.wangoon.biz.common.Result;
import cn.wangoon.biz.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 全局异常
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-09 09:42:16
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@RestControllerAdvice(basePackages = {"cn.wangoon.ms.bdm.web.controller"})
public class DefaultExceptionAdviceConfig {

    /**
     * @Description 业务异常处理 返回状态码:500
     * @Remark
     * @Params ==>
     * @Param e
     * @Return cn.wangoon.biz.common.Result
     * @Date 2022-10-09 09:42:00
     * @Auther YINZHIYU
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({BusinessException.class})
    public Result handleBusinessException(BusinessException e) {
        return Result.failure(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
    }

    /**
     * @Description 参数校验异常统一处理 返回状态码:400
     * @Remark
     * @Params ==>
     * @Param e
     * @Return cn.wangoon.biz.common.Result
     * @Date 2022-10-09 10:36:25
     * @Auther YINZHIYU
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.failure(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * @Description 所有异常统一处理 返回状态码:400
     * @Remark
     * @Params ==>
     * @Param e
     * @Return cn.wangoon.biz.common.Result
     * @Date 2022-10-09 10:38:29
     * @Auther YINZHIYU
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result handleException(BindException e) {
        return Result.failure(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * @Description 所有异常统一处理 返回状态码:500
     * @Remark
     * @Params ==>
     * @Param e
     * @Return cn.wangoon.biz.common.Result
     * @Date 2022-10-09 10:38:29
     * @Auther YINZHIYU
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return Result.failure(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
    }
}
