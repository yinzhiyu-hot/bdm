package cn.wangoon.ms.bdm.core.service.base;

import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.core.domain.dto.base.req.SyncTaskDto;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.vo.base.SyncTaskChartVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description 异步同步任务
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 14:09:16
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface SyncTaskService extends IService<SyncTask> {

    boolean insertSyncTask(SyncTask syncTask);

    Result deleteSyncTask(SyncTask syncTask);

    boolean batchInsertSyncTask(List<SyncTask> syncTaskList);

    boolean updateSyncTaskData(SyncTask syncTask);

    List<SyncTask> selectList(Wrapper<SyncTask> wrapper);

    List<SyncTask> getProcessTaskList(SyncTaskDto syncTaskDto);

    List<SyncTaskChartVo> getTaskChartsList(SyncTaskDto syncTaskDto);

    List<SyncTask> getProcessFailTask(String orderNumber);

    IPage<SyncTask> listSyncTaskByPage(Page<SyncTask> page, QueryWrapper queryWrapper);

    List<Long> getDataCarryForwardSyncTaskList(String createDate);

    void dataCarryForward();

    boolean executeWait(SyncTask syncTask);
}
