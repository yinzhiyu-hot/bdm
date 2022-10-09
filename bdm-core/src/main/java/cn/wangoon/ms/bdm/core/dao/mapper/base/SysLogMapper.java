package cn.wangoon.ms.bdm.core.dao.mapper.base;

import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 日志记录
 * @Remark
 * @PackagePath cn.wangoon.dao.mapper.SysLogMapper
 * @Author YINZHIYU
 * @Date 2020/9/27 10:32
 * @Version 1.0.0.0
 **/
public interface SysLogMapper extends BaseMapper<SysLog> {

    int dataCarryForward(String recordDate);

    int batchInsert(@Param("list") List<SysLog> list);
}