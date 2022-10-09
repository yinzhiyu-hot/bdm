package cn.wangoon.ms.bdm.core.dao.mapper.base;

import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 同步任务数据 Mapper 接口
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 13:54:23
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface SyncTaskDataMapper extends BaseMapper<SyncTaskData> {
    int batchInsert(@Param("list") List<SyncTaskData> list);
}
