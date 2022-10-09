package cn.wangoon.ms.bdm.core.domain.vo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 任务处理报表模型bean
 * @PackagePath cn.wangoon.domain.vo.SyncTaskChartVO
 * @Author YINZHIYU
 * @Date 2020/6/12 14:29
 * @Version 1.0.0.0
 **/
@Data
public class SyncTaskChartVo implements Serializable {
    public Integer numbers;
    public String date;
    public Integer taskStatus;
}
