package cn.wangoon.ms.bdm.core.domain.dto.base.req;

import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskData;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description 异步任务
 * @PackagePath com.wangoon.domain.dto.SyncTask
 * @Author YINZHIYU
 * @Date 2019-10-14 13:03:00
 * @Version 1.0.0.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SyncTaskDto extends SyncTask {

    private int mol;

    private int molValue;

    private String taskData;

    private Integer retryCount;

    private List<SyncTaskData> syncTaskDataList = Lists.newArrayList();
}
