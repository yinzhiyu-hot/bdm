package cn.wangoon.ms.bdm.core.domain.dto.base.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 系统日志
 * @PackagePath cn.wangoon.domain.dto.SysLogDto
 * @Author YINZHIYU
 * @Date 2020/5/25 13:54
 * @Version 1.0.0.0
 **/
@Data
public class SysLogDto implements Serializable {

    /**
     * businessType
     */
    private String businessType;

    /**
     * businessKey
     */
    private String businessKey;

    /**
     * 消息
     */
    private String message;

    /**
     * 记录时间
     */
    private Date recordDate;

    /**
     * 查询日期
     */
    private String queryDate;

    public SysLogDto() {
    }

    /**
     * 构造
     *
     * @Remark
     * @Params ==>
     * @Param message
     * @Return
     * @Date 2022-04-06 16:52:01
     * @Auther YINZHIYU
     */
    public SysLogDto(String message) {
        this.message = message;
        this.recordDate = new Date();
    }

    /*
     * @Description 构造
     * @Remark
     * @Params ==>
     * @Param businessKey
     * @Param message
     * @Return
     * @Date 2022-04-06 16:51:53
     * @Auther YINZHIYU
     */
    public SysLogDto(String businessKey, String message) {
        this.businessKey = businessKey;
        this.message = message;
        this.recordDate = new Date();
    }

    /*
     * @Description 构造
     * @Remark
     * @Params ==>
     * @Param businessType
     * @Param businessKey
     * @Param message
     * @Return
     * @Date 2022-04-06 16:51:46
     * @Auther YINZHIYU
     */
    public SysLogDto(String businessType, String businessKey, String message) {
        this.businessType = businessType;
        this.businessKey = businessKey;
        this.message = message;
        this.recordDate = new Date();
    }
}
