package cn.wangoon.ms.bdm.core.manager.base;

import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 异步任务接口
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-04-07 10:47:09
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
public interface SyncTaskManager {

    boolean insertSyncTask(SyncTask syncTask);

    boolean deleteSyncTask(SyncTask syncTask);

    boolean batchInsertSyncTask(List<SyncTask> syncTaskList);

    boolean updateSyncTaskData(SyncTask syncTask);

    int dataCarryForward(List<Long> idList);
}
