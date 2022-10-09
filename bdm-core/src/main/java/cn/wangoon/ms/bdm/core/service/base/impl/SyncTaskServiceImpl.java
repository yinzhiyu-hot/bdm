package cn.wangoon.ms.bdm.core.service.base.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.core.common.constants.SysBaseConfigConstants;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskStatusEnum;
import cn.wangoon.ms.bdm.core.dao.mapper.base.SyncTaskMapper;
import cn.wangoon.ms.bdm.core.domain.dto.base.req.SyncTaskDto;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import cn.wangoon.ms.bdm.core.domain.vo.base.SyncTaskChartVo;
import cn.wangoon.ms.bdm.core.manager.base.SyncTaskManager;
import cn.wangoon.ms.bdm.core.service.base.SyncTaskService;
import cn.wangoon.ms.bdm.core.service.base.SysLogService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 异步任务
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 14:09:36
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTask> implements SyncTaskService {

    @Resource
    private SyncTaskManager syncTaskManager;

    @Resource
    private SysLogService sysLogService;

    @Override
    public boolean insertSyncTask(SyncTask syncTask) {
        return syncTaskManager.insertSyncTask(syncTask);
    }

    @Override
    public Result deleteSyncTask(SyncTask syncTask) {
        if (ObjectUtil.isEmpty(syncTask) || ObjectUtil.isEmpty(syncTask.getId())) {
            return Result.failure("参数不全[任务为空/任务ID为空]");
        }
        try {
            //校验出库任务是否
            boolean result = syncTaskManager.deleteSyncTask(syncTask);
            if (!result) {
                return Result.failure("处理失败");
            }
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
        return Result.success("处理成功");
    }

    @Override
    public boolean batchInsertSyncTask(List<SyncTask> syncTaskList) {
        return syncTaskManager.batchInsertSyncTask(syncTaskList);
    }

    @Override
    public boolean updateSyncTaskData(SyncTask syncTask) {
        return syncTaskManager.updateSyncTaskData(syncTask);
    }

    @Override
    public List<SyncTask> selectList(Wrapper<SyncTask> wrapper) {
        return super.baseMapper.selectList(wrapper);
    }

    @Override
    public List<SyncTask> getProcessTaskList(SyncTaskDto syncTaskDto) {
        return this.baseMapper.getProcessTaskList(syncTaskDto);
    }

    @Override
    public List<SyncTaskChartVo> getTaskChartsList(SyncTaskDto syncTaskDto) {
        return this.baseMapper.getTaskChartsList(syncTaskDto);
    }

    @Override
    public List<SyncTask> getProcessFailTask(String orderNumber) {
        return this.baseMapper.getProcessFailTask(orderNumber);
    }

    @Override
    public IPage<SyncTask> listSyncTaskByPage(Page<SyncTask> page, QueryWrapper queryWrapper) {
        return this.baseMapper.listSyncTaskByPage(page, queryWrapper);
    }

    @Override
    public List<Long> getDataCarryForwardSyncTaskList(String createDate) {
        return this.baseMapper.getDataCarryForwardSyncTaskList(createDate);
    }

    @Override
    public void dataCarryForward() {
        String createDate = DateUtil.format(DateUtil.offsetDay(DateUtil.date(), SysBaseConfigConstants.TASK_CARRY_FORWARD_DAY_OFFSET), DatePattern.NORM_DATE_PATTERN);
        List<Long> idList = getDataCarryForwardSyncTaskList(createDate);
        int count = syncTaskManager.dataCarryForward(idList);
        sysLogService.recordLog(new SysLog("dataCarryForward", String.format("本次结转sync_task任务表数据记录 %s 条", count)));
    }

    @Override
    public boolean executeWait(SyncTask syncTask) {
        syncTask.setTaskStatus(SyncTaskStatusEnum.EXCUTING_WAIT.getTaskStatus());
        syncTask.setProcessCount(0);
        return this.updateById(syncTask);
    }
}
