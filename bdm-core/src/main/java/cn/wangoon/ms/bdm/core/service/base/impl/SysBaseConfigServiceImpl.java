package cn.wangoon.ms.bdm.core.service.base.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.core.common.enums.DelFlagEnum;
import cn.wangoon.ms.bdm.core.dao.mapper.base.SysBaseConfigMapper;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysBaseConfig;
import cn.wangoon.ms.bdm.core.domain.query.base.SysBaseConfigQuery;
import cn.wangoon.ms.bdm.core.service.base.SysBaseConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 基础信息配置实体
 * @PackagePath cn.wangoon.service.business.impl.SysBaseConfigServiceImpl
 * @Author YINZHIYU
 * @Date 2020/4/26 9:56
 * @Version 1.0.0.0
 **/
@Service
public class SysBaseConfigServiceImpl extends ServiceImpl<SysBaseConfigMapper, SysBaseConfig> implements SysBaseConfigService {

    @Override
    public int batchInsert(List<SysBaseConfig> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public List<SysBaseConfig> listSysBaseConfigByCondition(SysBaseConfigQuery sysBaseConfigQuery) {
        return baseMapper.listSysBaseConfigByCondition(convertWrapper(sysBaseConfigQuery));
    }

    private QueryWrapper<SysBaseConfig> convertWrapper(SysBaseConfigQuery sysBaseConfigQuery) {
        QueryWrapper<SysBaseConfig> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(sysBaseConfigQuery.getBizType())) {
            queryWrapper.eq(SysBaseConfig.COL_BIZ_TYPE, sysBaseConfigQuery.getBizType());
        }
        if (ObjectUtil.isNotEmpty(sysBaseConfigQuery.getBizTypeList())) {
            queryWrapper.in(SysBaseConfig.COL_BIZ_TYPE, sysBaseConfigQuery.getBizTypeList());
        }
        if (ObjectUtil.isNotEmpty(sysBaseConfigQuery.getBizKey())) {
            queryWrapper.eq(SysBaseConfig.COL_BIZ_KEY, sysBaseConfigQuery.getBizKey());
        }
        if (ObjectUtil.isNotEmpty(sysBaseConfigQuery.getBizKeyList())) {
            queryWrapper.in(SysBaseConfig.COL_BIZ_KEY, sysBaseConfigQuery.getBizKeyList());
        }
        queryWrapper.eq(SysBaseConfig.COL_DEL_FLAG, DelFlagEnum.NO.getFlag());
        return queryWrapper;
    }

    @Override
    public Result listSysBaseConfig(String json) {
        try {
            SysBaseConfigQuery sysBaseConfigQuery = JSONUtil.toBean(json, SysBaseConfigQuery.class);
            return Result.success(baseMapper.listSysBaseConfigByCondition(convertWrapper(sysBaseConfigQuery)));
        } catch (Exception e) {
            return Result.failure(String.format("基础配置查询异常：%s", e));
        }
    }
}
