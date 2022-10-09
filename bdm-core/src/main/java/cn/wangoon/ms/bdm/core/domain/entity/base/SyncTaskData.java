package cn.wangoon.ms.bdm.core.domain.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 同步任务数据
 * </p>
 *
 * @author yinzhiyu
 * @since 2019-10-14
 */
@Data
@TableName("sync_task_data")
public class SyncTaskData implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务表主键
     */
    @TableField("task_id")
    private Long taskId;

    /**
     * 任务数据
     */
    @TableField("task_data")
    private String taskData;

    /**
     * 截断顺序
     */
    @TableField("data_index")
    private Integer dataIndex;

    public static final String COL_ID = "id";

    public static final String COL_TASK_ID = "task_id";

    public static final String COL_TASK_DATA = "task_data";

    public static final String COL_DATA_INDEX = "data_index";
}
