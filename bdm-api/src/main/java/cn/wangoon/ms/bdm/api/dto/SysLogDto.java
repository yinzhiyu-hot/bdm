package cn.wangoon.ms.bdm.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 系统日志
 * @PackagePath cn.wangoon.domain.dto.SysLogDto
 * @Author YINZHIYU
 * @Date 2020/5/25 13:54
 * @Version 1.0.0.0
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String recordDate;

    /**
     * 查询日期
     */
    private String queryDate;
}
