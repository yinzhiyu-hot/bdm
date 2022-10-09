package cn.wangoon.ms.bdm.core.common.enums;

import cn.hutool.core.util.StrUtil;
import cn.wangoon.ms.bdm.core.service.biz.demo.impl.DemoJobServiceImpl;

/**
 * @Description 同步任务类型枚举
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-04-06 21:17:31
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public enum SyncTaskTypeEnum {

    DEMO_DO("示例 ==> 做任务", "DEMO_DO", DemoJobServiceImpl.class, "demoDo");

    // 成员变量
    private String remark;

    // 任务类型
    private String syncTaskType;

    // 处理类
    private Class clazz;

    // JOB调度处理中配置的业务bean 对应的处理方法[方法的返回和出入参，统一按 public boolean xxxXxxxx(SyncTask syncTask) 格式进行编写]
    private String method;

    // 构造方法
    SyncTaskTypeEnum(String remark, String syncTaskType, Class clazz, String method) {
        this.remark = remark;
        this.syncTaskType = syncTaskType;
        this.clazz = clazz;
        this.method = method;
    }

    public static String getRemark(String syncTaskType) {
        for (SyncTaskTypeEnum syncTaskTypeEnum : SyncTaskTypeEnum.values()) {
            if (StrUtil.equals(syncTaskTypeEnum.getSyncTaskType(), syncTaskType, true)) {
                return syncTaskTypeEnum.getRemark();
            }
        }
        return StrUtil.EMPTY;
    }

    public static String getMethod(String syncTaskType) {
        for (SyncTaskTypeEnum syncTaskTypeEnum : SyncTaskTypeEnum.values()) {
            if (StrUtil.equals(syncTaskTypeEnum.getSyncTaskType(), syncTaskType, true)) {
                return syncTaskTypeEnum.getMethod();
            }
        }
        return StrUtil.EMPTY;
    }

    public static SyncTaskTypeEnum getSyncTaskTypeEnum(String syncTaskType) {
        for (SyncTaskTypeEnum syncTaskTypeEnum : SyncTaskTypeEnum.values()) {
            if (StrUtil.equals(syncTaskTypeEnum.getSyncTaskType(), syncTaskType, true)) {
                return syncTaskTypeEnum;
            }
        }
        return null;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSyncTaskType() {
        return syncTaskType;
    }

    public void setSyncTaskType(String syncTaskType) {
        this.syncTaskType = syncTaskType;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
