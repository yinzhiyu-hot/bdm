package cn.wangoon.ms.bdm.core.manager.base.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.wangoon.ms.bdm.core.dao.mapper.base.SyncTaskDataMapper;
import cn.wangoon.ms.bdm.core.dao.mapper.base.SyncTaskMapper;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskData;
import cn.wangoon.ms.bdm.core.manager.base.SyncTaskManager;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 异步任务
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-04-07 10:46:59
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Component
@Slf4j
public class SyncTaskManagerImpl implements SyncTaskManager {

    @Resource
    private SyncTaskMapper syncTaskMapper;

    @Resource
    private SyncTaskDataMapper syncTaskDataMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSyncTask(SyncTask syncTask) {
        boolean result;
        try {
            //插入主档
            syncTask.setId(null);
            result = SqlHelper.retBool(syncTaskMapper.insert(syncTask));

            if (!result) {
                throw new RuntimeException("syncTaskMapper.insert(syncTask) return false");
            }
            //插入明细
            syncTask.getSyncTaskDataList().stream().filter(bean -> {
                bean.setTaskId(syncTask.getId());
                return true;
            }).collect(Collectors.toList());

            result = SqlHelper.retBool(syncTaskDataMapper.batchInsert(syncTask.getSyncTaskDataList()));

            if (!result) {
                throw new RuntimeException("syncTaskDataMapper.batchInsert(syncTask.getSyncTaskDataList()) return false");
            }

        } catch (Exception ex) {
            result = false;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
            log.error("持久化异步任务异常, syncTask:{}", syncTask.toString(), ex);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSyncTask(SyncTask syncTask) {
        boolean result;
        try {
            //删除明细
            QueryWrapper<SyncTaskData> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq(SyncTaskData.COL_TASK_ID, syncTask.getId());
            result = SqlHelper.retBool(syncTaskDataMapper.delete(deleteWrapper));

            if (!result) {
                throw new RuntimeException("syncTaskDataMapper.delete(deleteWrapper) return false");
            }

            //删除主档
            result = SqlHelper.retBool(syncTaskMapper.deleteById(syncTask.getId()));

            if (!result) {
                throw new RuntimeException("syncTaskMapper.deleteById(syncTask.getId()) return false");
            }

        } catch (Exception ex) {
            result = false;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
            log.error("删除异步任务异常, syncTask:{}", JSONUtil.toJsonStr(syncTask), ex);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchInsertSyncTask(List<SyncTask> syncTaskList) {
        boolean result = false;
        try {
            for (SyncTask syncTask : syncTaskList) {
                //插入主档
                syncTask.setId(null);
                result = SqlHelper.retBool(syncTaskMapper.insert(syncTask));

                if (!result) {
                    throw new RuntimeException("syncTaskMapper.insert(syncTask) return false");
                }
                //插入明细
                syncTask.getSyncTaskDataList().stream().filter(bean -> {
                    bean.setTaskId(syncTask.getId());
                    return true;
                }).collect(Collectors.toList());

                result = SqlHelper.retBool(syncTaskDataMapper.batchInsert(syncTask.getSyncTaskDataList()));

                if (!result) {
                    throw new RuntimeException("syncTaskDataMapper.batchInsert(syncTask.getSyncTaskDataList()) return false");
                }
            }
        } catch (Exception ex) {
            result = false;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
            log.error("批量持久化异步任务异常, syncTaskList:{}", JSONUtil.toJsonStr(syncTaskList), ex);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncTaskData(SyncTask syncTask) {
        boolean result;
        try {
            QueryWrapper<SyncTaskData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SyncTaskData.COL_TASK_ID, syncTask.getId());
            result = SqlHelper.retBool(syncTaskDataMapper.delete(queryWrapper));
            if (!result) {
                throw new RuntimeException("syncTaskDataMapper.delete(queryWrapper) return false");
            }

            //插入明细
            syncTask.getSyncTaskDataList().stream().filter(bean -> {
                bean.setTaskId(syncTask.getId());
                return true;
            }).collect(Collectors.toList());

            result = SqlHelper.retBool(syncTaskDataMapper.batchInsert(syncTask.getSyncTaskDataList()));

            if (!result) {
                throw new RuntimeException("syncTaskDataMapper.batchInsert(syncTask.getSyncTaskDataList()) return false");
            }

        } catch (Exception ex) {
            result = false;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
            log.error("更新异步任务数据异常, syncTask:{}", JSONUtil.toJsonStr(syncTask), ex);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int dataCarryForward(List<Long> idList) {
        int delteCount = 0;
        try {
            if (ObjectUtil.isEmpty(idList)) {
                return delteCount;
            }

            QueryWrapper<SyncTaskData> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(SyncTaskData.COL_TASK_ID, idList);
            boolean result = SqlHelper.retBool(syncTaskDataMapper.delete(queryWrapper));

            if (!result) {
                throw new RuntimeException("syncTaskDataMapper.delete(queryWrapper) return false");
            }
            delteCount = syncTaskMapper.deleteBatchIds(idList);
            result = SqlHelper.retBool(delteCount);
            if (!result) {
                throw new RuntimeException("syncTaskMapper.deleteBatchIds(idList) return false");
            }

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//关键
            log.error("结转任务异常, idList:{}", JSONUtil.toJsonStr(idList), ex);
        }
        return delteCount;
    }
}
