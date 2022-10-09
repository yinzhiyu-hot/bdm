package cn.wangoon.ms.bdm.job.handlers;

import cn.hutool.core.util.ObjectUtil;
import cn.wangoon.ms.bdm.core.common.cache.impl.SysBaseConfigCache;
import cn.wangoon.ms.bdm.core.common.constants.JobsConstants;
import cn.wangoon.ms.bdm.core.common.constants.SysBaseConfigConstants;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskStatusEnum;
import cn.wangoon.ms.bdm.core.domain.dto.base.req.SyncTaskDto;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskData;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskException;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import cn.wangoon.ms.bdm.core.service.base.SyncTaskDataService;
import cn.wangoon.ms.bdm.core.service.base.SyncTaskExceptionService;
import cn.wangoon.ms.bdm.core.service.base.SyncTaskService;
import cn.wangoon.ms.bdm.core.service.base.SysLogService;
import cn.wangoon.ms.bdm.job.adapter.DataSyncAdapter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description 基础JOB
 * @Remark 基于任务(SyncTask) 的通用处理JOB
 * @Author YINZHIYU
 * @Date 2022-04-06 19:48:41
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
public abstract class BaseSimpleJob {

    @Resource
    private DataSyncAdapter dataSyncAdapter;

    @Resource
    private SyncTaskService syncTaskService;

    @Resource
    private SyncTaskDataService syncTaskDataService;

    @Resource
    private SyncTaskExceptionService syncTaskExceptionService;

    @Resource
    private SysLogService sysLogService;

    /**
     * @Description 开始执行任务
     * @Remark
     * @Params ==>
     * @Param taskType
     * @Return void
     * @Date 2022-04-06 19:44:53
     * @Auther YINZHIYU
     */
    public void startExecuteJob(String taskType) {
        //根据分片规则取任务数据
        SyncTaskDto syncTaskDto = new SyncTaskDto();

        if (ObjectUtil.isEmpty(taskType)) {
            return;
        }
        syncTaskDto.setRetryCount(getRetryCountFromGlobalMap());
        syncTaskDto.setTaskType(taskType);

        //获取执行任务
        List<SyncTask> syncTaskList = syncTaskService.getProcessTaskList(syncTaskDto);

        if (ObjectUtil.isEmpty(syncTaskList)) {
            return;
        }

        for (SyncTask syncTask : syncTaskList) {
            try {
                //开始处理，更新状态处理中
                syncTask.setProcessCount(syncTask.getProcessCount() + 1);
                syncTask.setTaskStatus(SyncTaskStatusEnum.EXCUTING.getTaskStatus());
                syncTaskService.updateById(syncTask);

                //获取执行任务对应的业务数据，并进行重组
                QueryWrapper<SyncTaskData> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(SyncTaskData.COL_TASK_ID, syncTask.getId());
                queryWrapper.orderByAsc(SyncTaskData.COL_DATA_INDEX);
                List<SyncTaskData> syncTaskDataList = syncTaskDataService.getBaseMapper().selectList(queryWrapper);
                StringBuilder caclueData = new StringBuilder();
                for (SyncTaskData syncTaskData : syncTaskDataList) {
                    caclueData.append(syncTaskData.getTaskData());
                }

                String taskData = caclueData.toString();

                //容错处理
                if (ObjectUtil.isEmpty(taskData)) {
                    continue;
                }
                syncTask.setSyncTaskDataList(syncTaskDataList);
                syncTask.setTaskData(taskData);

                //根据task_type 进行业务路由，由不同的入口进行业务处理
                boolean excuteResult = dataSyncAdapter.excute(syncTask);

                if (!excuteResult) {
                    //最后一次重试失败，则更新处理失败
                    //需重新查询一次[避免重试次数=1的情况下，有控制顺序<执行等待状态{如果状态变为执行等待，执行次数会重置为0}>的任务执行一次后，变成处理失败]
                    SyncTask syncTaskCalculate = syncTaskService.getById(syncTask.getId());

                    if (syncTaskCalculate.getProcessCount() >= getRetryCountFromGlobalMap()) {
                        syncTaskCalculate.setTaskStatus(SyncTaskStatusEnum.FAIL.getTaskStatus());
                        syncTaskService.updateById(syncTaskCalculate);

                        //TODO 最后一次重试失败，发失败预警
                    }
                    continue;
                }
                //结束处理，更新状态已处理
                syncTask.setTaskStatus(SyncTaskStatusEnum.FINISH.getTaskStatus());
                syncTask.setFinishDate(new Date());
                syncTaskService.updateById(syncTask);
            } catch (Exception ex) {
                //开始处理，更新状态处理中
                syncTask.setTaskStatus(SyncTaskStatusEnum.EXCEPTION.getTaskStatus());
                syncTaskService.updateById(syncTask);

                String exStr = String.format("BaseSimpleJob ==> execute ==> 任务处理异常：%s", ex);
                SyncTaskException syncTaskException = new SyncTaskException();
                syncTaskException.setTaskId(syncTask.getId());
                syncTaskException.setTaskException(exStr.length() > 10000 ? exStr.substring(0, 10000) : exStr);
                syncTaskExceptionService.getBaseMapper().insert(syncTaskException);

                //记录异常日志
                sysLogService.recordLog(new SysLog(syncTask.getTaskType(), syncTask.getId().toString(), exStr));

                //TODO 处理异常，发异常预警
            }
        }
    }

    /**
     * @Description 获取任务同步重试次数配置
     * @Remark
     * @Params ==>
     * @Return java.lang.Integer
     * @Date 2022-04-06 19:48:54
     * @Auther YINZHIYU
     */
    protected Integer getRetryCountFromGlobalMap() {
        return SysBaseConfigCache.getSysBaseConfigFromGlobalMap(SysBaseConfigConstants.RETRY_COUNT, SysBaseConfigConstants.RETRY_COUNT_SYNC_TASK, JobsConstants.RETRY_COUNT);
    }
}
