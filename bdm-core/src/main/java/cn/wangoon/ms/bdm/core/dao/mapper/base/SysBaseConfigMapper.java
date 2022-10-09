package cn.wangoon.ms.bdm.core.dao.mapper.base;

import cn.wangoon.ms.bdm.core.domain.entity.base.SysBaseConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 基础信息配置实体
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 13:57:03
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface SysBaseConfigMapper extends BaseMapper<SysBaseConfig> {

    int batchInsert(@Param("list") List<SysBaseConfig> list);

    List<SysBaseConfig> listSysBaseConfigByCondition(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}