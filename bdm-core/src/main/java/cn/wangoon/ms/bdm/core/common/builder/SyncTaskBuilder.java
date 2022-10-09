package cn.wangoon.ms.bdm.core.common.builder;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskStatusEnum;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskTypeEnum;
import cn.wangoon.ms.bdm.core.common.utils.StringUtils;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskData;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 异步任务生成器
 * @Remark
 * @PackagePath cn.wangoon.service.builder.SyncTaskBuilder
 * @Author YINZHIYU
 * @Date 2021/3/12 12:05
 * @Version 1.0.0.0
 **/
@Component
@Scope("prototype")
public class SyncTaskBuilder<T> {

    /*
     * @Description 批量转换异步任务
     * @Params ==>
     * @Param tList
     * @Param syncTaskTypeEnum
     * @Return java.util.List<cn.wangoon.domain.entity.SyncTask>
     * @Date 2020/5/29 14:17
     * @Auther YINZHIYU
     */
    public List<SyncTask> convertSyncTasks(List<T> tList, SyncTaskTypeEnum syncTaskTypeEnum) {
        List<SyncTask> syncTaskList = Lists.newArrayList();
        for (T t : tList) {
            syncTaskList.add(convertSyncTask(t, syncTaskTypeEnum));
        }
        return syncTaskList;
    }

    /*
     * @Description 单个转换异步任务
     * @Remark
     * @Params ==>
     * @Param t
     * @Param syncTaskTypeEnum
     * @Param remark
     * @Return cn.wangoon.domain.entity.SyncTask
     * @Date 2022-04-06 21:09:09
     * @Auther YINZHIYU
     */
    public SyncTask convertSyncTask(T t, SyncTaskTypeEnum syncTaskTypeEnum, String remark) {

        //组装任务主档
        SyncTask syncTask = new SyncTask();
        syncTask.setTaskType(syncTaskTypeEnum.getSyncTaskType());
        syncTask.setTaskDesc(syncTaskTypeEnum.getRemark());
        if (ObjectUtil.isNotEmpty(remark)) {
            syncTask.setTaskDesc(String.format("%s ==> %s", syncTaskTypeEnum.getRemark(), remark));
        }
        syncTask.setTaskStatus(SyncTaskStatusEnum.WAIT.getTaskStatus());
        syncTask.setProcessCount(0);
        syncTask.setCreateDate(DateUtil.date());

        //组装任务明细
        syncTask.setSyncTaskDataList(convertSyncTaskDatas(t));

        return syncTask;
    }


    /*
     * @Description 单个转换异步任务
     * @Remark
     * @Params ==>
     * @Param t
     * @Param syncTaskTypeEnum
     * @Param remark
     * @Return cn.wangoon.domain.entity.SyncTask
     * @Date 2022-04-06 21:09:09
     * @Auther YINZHIYU
     */
    public SyncTask convertSyncTaskByArray(T t, SyncTaskTypeEnum syncTaskTypeEnum, String remark) {

        //组装任务主档
        SyncTask syncTask = new SyncTask();
        syncTask.setTaskType(syncTaskTypeEnum.getSyncTaskType());
        syncTask.setTaskDesc(syncTaskTypeEnum.getRemark());
        if (ObjectUtil.isNotEmpty(remark)) {
            syncTask.setTaskDesc(String.format("%s ==> %s", syncTaskTypeEnum.getRemark(), remark));
        }
        syncTask.setTaskStatus(SyncTaskStatusEnum.WAIT.getTaskStatus());
        syncTask.setProcessCount(0);
        syncTask.setCreateDate(DateUtil.date());

        //组装任务明细
        syncTask.setSyncTaskDataList(convertSyncTaskDatasByArray(t));

        return syncTask;
    }

    /*
     * @Description 单个转换异步任务
     * @Params ==>
     * @Param t
     * @Param syncTaskTypeEnum
     * @Return cn.wangoon.domain.entity.SyncTask
     * @Date 2020/5/29 14:17
     * @Auther YINZHIYU
     */
    public SyncTask convertSyncTask(T t, SyncTaskTypeEnum syncTaskTypeEnum) {

        //组装任务主档
        SyncTask syncTask = new SyncTask();
        syncTask.setTaskType(syncTaskTypeEnum.getSyncTaskType());
        syncTask.setTaskDesc(syncTaskTypeEnum.getRemark());
        syncTask.setTaskStatus(SyncTaskStatusEnum.WAIT.getTaskStatus());
        syncTask.setProcessCount(0);
        syncTask.setCreateDate(DateUtil.date());

        //组装任务明细
        syncTask.setSyncTaskDataList(convertSyncTaskDatas(t));

        return syncTask;
    }

    /*
     * @Description 单个转换异步任务
     * @Params ==>
     * @Param t
     * @Param syncTaskTypeEnum
     * @Return cn.wangoon.domain.entity.SyncTask
     * @Date 2020/5/29 14:17
     * @Auther YINZHIYU
     */
    public SyncTask convertSyncTaskByArray(T t, SyncTaskTypeEnum syncTaskTypeEnum) {

        //组装任务主档
        SyncTask syncTask = new SyncTask();
        syncTask.setTaskType(syncTaskTypeEnum.getSyncTaskType());
        syncTask.setTaskDesc(syncTaskTypeEnum.getRemark());
        syncTask.setTaskStatus(SyncTaskStatusEnum.WAIT.getTaskStatus());
        syncTask.setProcessCount(0);
        syncTask.setCreateDate(DateUtil.date());

        //组装任务明细
        syncTask.setSyncTaskDataList(convertSyncTaskDatasByArray(t));

        return syncTask;
    }

    /*
     * @Description 转换明细
     * @Params ==>
     * @Param t
     * @Return java.util.List<cn.wangoon.domain.entity.SyncTaskData>
     * @Date 2020/5/29 14:08
     * @Auther YINZHIYU
     */
    private List<SyncTaskData> convertSyncTaskDatas(T t) {
        //        String taskData = StringUtils.base64Encode(JSONUtil.toJsonStr(JSONUtil.parseObj(t, true)));
        if (ObjectUtil.isEmpty(t)) {
            return null;
        }
        String taskData = JSONUtil.toJsonStr(t, JSONConfig.create().setDateFormat(DatePattern.NORM_DATETIME_PATTERN));
        String[] taskDataArray = StringUtils.splitString(taskData, 10000);//10000长度进行字符串分割
        List<SyncTaskData> syncTaskDataList = Lists.newArrayList();
        for (int i = 0; i < taskDataArray.length; i++) {
            SyncTaskData syncTaskData = new SyncTaskData();
            syncTaskData.setTaskData(taskDataArray[i]);
            syncTaskData.setDataIndex(i);
            syncTaskDataList.add(syncTaskData);
        }
        return syncTaskDataList;
    }


    /*
     * @Description 转换明细
     * @Params ==>
     * @Param t
     * @Return java.util.List<cn.wangoon.domain.entity.SyncTaskData>
     * @Date 2020/5/29 14:08
     * @Auther YINZHIYU
     */
    private List<SyncTaskData> convertSyncTaskDatasByArray(T t) {
        //        String taskData = StringUtils.base64Encode(JSONUtil.toJsonStr(JSONUtil.parseObj(t, true)));
        String taskData = JSONUtil.toJsonStr(JSONUtil.parseArray(t, true));
        String[] taskDataArray = StringUtils.splitString(taskData, 10000);//10000长度进行字符串分割
        List<SyncTaskData> syncTaskDataList = Lists.newArrayList();
        for (int i = 0; i < taskDataArray.length; i++) {
            SyncTaskData syncTaskData = new SyncTaskData();
            syncTaskData.setTaskData(taskDataArray[i]);
            syncTaskData.setDataIndex(i);
            syncTaskDataList.add(syncTaskData);
        }
        return syncTaskDataList;
    }

    /*
     * @Description 转换明细
     * @Params ==>
     * @Param t
     * @Return java.util.List<cn.wangoon.domain.entity.SyncTaskData>
     * @Date 2020/5/29 14:08
     * @Auther YINZHIYU
     */
    public List<SyncTaskData> convertSyncTaskDatas(String taskDataJson) {
        //        String taskData = StringUtils.base64Encode(taskDataJson);
        String[] taskDataArray = StringUtils.splitString(taskDataJson, 10000);//10000长度进行字符串分割
        List<SyncTaskData> syncTaskDataList = Lists.newArrayList();
        for (int i = 0; i < taskDataArray.length; i++) {
            SyncTaskData syncTaskData = new SyncTaskData();
            syncTaskData.setTaskData(taskDataArray[i]);
            syncTaskData.setDataIndex(i);
            syncTaskDataList.add(syncTaskData);
        }
        return syncTaskDataList;
    }
}
