package cn.wangoon.ms.bdm.core.service.base;

import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 日志
 * @Remark
 * @PackagePath cn.wangoon.service.business.base.SysLogService
 * @Author YINZHIYU
 * @Date 2021/3/12 12:06
 * @Version 1.0.0.0
 **/
public interface SysLogService extends IService<SysLog> {

    void recordLog(SysLog sysLog);

    boolean batchInsert(@Param("list") List<SysLog> list);

    void dataCarryForward();
}
