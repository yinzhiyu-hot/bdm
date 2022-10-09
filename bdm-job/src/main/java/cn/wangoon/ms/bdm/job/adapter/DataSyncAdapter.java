package cn.wangoon.ms.bdm.job.adapter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskTypeEnum;
import cn.wangoon.ms.bdm.core.common.utils.CastUtil;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.job.config.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @Description 数据同步适配器
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-04-06 21:22:45
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Service
@Slf4j
public class DataSyncAdapter {

    /**
     * 数据同步执行方法
     *
     * @param syncTask
     * @return
     */
    public boolean excute(SyncTask syncTask) {

        boolean excuteResult = false;

        String radomStr = RandomUtil.randomString(6);

        try {

            // 程序开始时间
            Instant startTime = Instant.now();

            SyncTaskTypeEnum syncTaskTypeEnum = SyncTaskTypeEnum.getSyncTaskTypeEnum(syncTask.getTaskType());

            if (ObjectUtil.isEmpty(syncTaskTypeEnum)) {
                throw new RuntimeException(String.format("无此业务类型[%s]", syncTask.getTaskType()));
            }

            excuteResult = adapter(syncTaskTypeEnum, syncTask);

            // 程序结束时间
            Instant endTime = Instant.now();

            // 毫秒
            long millis = ChronoUnit.MILLIS.between(startTime, endTime);

            log.error("==> [{}]Thread ID: {}, DataSyncAdapter 数据同步结束, 同步 {}, 执行耗时 {} ms。", radomStr, Thread.currentThread().getId(), excuteResult ? "成功" : "失败", millis);

        } catch (Exception e) {
            String exceptionMessage = String.format("%s ==> %s", JSONUtil.toJsonStr(e), e.getMessage());
            if (e instanceof InvocationTargetException) {
                InvocationTargetException invocationTargetException = CastUtil.cast(e);
                exceptionMessage = JSONUtil.toJsonStr(invocationTargetException.getTargetException());
            }
            throw new RuntimeException(String.format("[%s]Thread ID: %s, DataSyncAdapter 数据同步异常 ==> %s", radomStr, Thread.currentThread().getId(), exceptionMessage));
        }
        return excuteResult;
    }

    protected boolean adapter(SyncTaskTypeEnum syncTaskTypeEnum, SyncTask syncTask) throws Exception {
        Method method = syncTaskTypeEnum.getClazz().getMethod(syncTaskTypeEnum.getMethod(), SyncTask.class);
        return CastUtil.cast(method.invoke(ApplicationConfig.getCtx().getBean(syncTaskTypeEnum.getClazz()), syncTask));
    }
}
