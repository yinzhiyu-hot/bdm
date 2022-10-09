package cn.wangoon.ms.bdm.core.dao.mapper.base;

import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 任务异常信息 Mapper 接口
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 13:54:44
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface SyncTaskExceptionMapper extends BaseMapper<SyncTaskException> {
    int batchInsert(@Param("list") List<SyncTaskException> list);
}
