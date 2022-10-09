package cn.wangoon.ms.bdm.core.dao.mapper.base;

import cn.wangoon.ms.bdm.core.domain.dto.base.req.SyncTaskDto;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.vo.base.SyncTaskChartVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 异步任务 Mapper 接口
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 13:55:34
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface SyncTaskMapper extends BaseMapper<SyncTask> {

    List<SyncTask> getProcessTaskList(SyncTaskDto syncTaskDto);

    List<SyncTaskChartVo> getTaskChartsList(SyncTaskDto syncTaskDto);

    List<SyncTask> getProcessFailTask(String orderNumber);

    IPage<SyncTask> listSyncTaskByPage(Page<SyncTask> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    List<Long> getDataCarryForwardSyncTaskList(String createDate);

    int batchInsert(@Param("list") List<SyncTask> list);
}
