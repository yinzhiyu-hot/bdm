package cn.wangoon.ms.bdm.core.domain.query.base;

import cn.wangoon.ms.bdm.core.domain.entity.base.SysBaseConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 基础信息配置查询实体
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 13:59:51
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysBaseConfigQuery extends SysBaseConfig implements Serializable {
    List<String> bizKeyList;
    List<String> bizTypeList;
}