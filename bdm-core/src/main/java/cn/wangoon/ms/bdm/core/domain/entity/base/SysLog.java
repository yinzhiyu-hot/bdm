package cn.wangoon.ms.bdm.core.domain.entity.base;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.wangoon.ms.bdm.core.common.utils.StringUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 系统日志
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 13:40:05
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_log")
public class SysLog implements Serializable {
    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务类型
     */
    @TableField(value = "business_type")
    private String businessType;

    /**
     * 业务键(订单号等等)
     */
    @TableField(value = "business_key")
    private String businessKey;

    /**
     * 日志消息
     */
    @TableField(value = "message")
    private String message;

    /**
     * 记录日期
     */
    @TableField(value = "record_date")
    private String recordDate;

    /**
     * 时间戳
     */
    @TableField(value = "ts")
    private Date ts;

    public SysLog(String businessType, String businessKey, String message) {
        this.businessType = businessType;
        this.businessKey = businessKey;
        this.message = StringUtils.splitString(message, 10000)[0];
        this.recordDate = DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN);
    }

    public SysLog(String businessKey, String message) {
        this.businessKey = businessKey;
        this.message = StringUtils.splitString(message, 10000)[0];
        this.recordDate = DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN);
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_BUSINESS_TYPE = "business_type";

    public static final String COL_BUSINESS_KEY = "business_key";

    public static final String COL_MESSAGE = "message";

    public static final String COL_RECORD_DATE = "record_date";

    public static final String COL_TS = "ts";
}