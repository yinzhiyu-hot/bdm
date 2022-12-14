package cn.wangoon.ms.bdm.core.domain.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 基础信息配置实体
 * @PackagePath cn.wangoon.domain.entity.SysBaseConfig
 * @Author YINZHIYU
 * @Date 2020/4/26 9:56
 * @Version 1.0.0.0
 **/
@Data
@TableName(value = "sys_base_config")
public class SysBaseConfig {
    /**
     * 基础配置表主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 业务类型
     */
    @TableField(value = "biz_type")
    private String bizType;

    /**
     * 业务配置键
     */
    @TableField(value = "biz_key")
    private String bizKey;

    /**
     * 业务配置值
     */
    @TableField(value = "biz_value")
    private String bizValue;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 是否删除 0 否 1 是
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

    /**
     * 时间戳
     */
    @TableField(value = "ts")
    private Date ts;

    /**
     * 更新标识
     */
    @TableField(exist = false)
    private boolean updateFlag;
    /**
     * 更新IP
     */
    @TableField(exist = false)
    private String updateIpPort;
    /**
     * 操作说明
     */
    @TableField(exist = false)
    private String operateDesc;

    public static final String COL_ID = "id";

    public static final String COL_BIZ_TYPE = "biz_type";

    public static final String COL_BIZ_KEY = "biz_key";

    public static final String COL_BIZ_VALUE = "biz_value";

    public static final String COL_REMARK = "remark";

    public static final String COL_DEL_FLAG = "del_flag";

    public static final String COL_TS = "ts";
}