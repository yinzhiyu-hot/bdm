package cn.wangoon.ms.bdm.core.service.base;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysBaseConfig;
import cn.wangoon.ms.bdm.core.domain.query.base.SysBaseConfigQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description 基础信息配置
 * @Remark
 * @PackagePath cn.wangoon.service.business.base.SysBaseConfigService
 * @Author YINZHIYU
 * @Date 2021/3/12 12:07
 * @Version 1.0.0.0
 **/
public interface SysBaseConfigService extends IService<SysBaseConfig> {

    int batchInsert(List<SysBaseConfig> list);

    List<SysBaseConfig> listSysBaseConfigByCondition(SysBaseConfigQuery sysBaseConfigQuery);

    Result<Object> listSysBaseConfig(String json);
}
