package cn.wangoon.ms.basedata;


import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import cn.wangoon.ms.basedata.strategy.ProcessStrategy;
import cn.wangoon.ms.basedata.strategy.StrategyEnum;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * @Description 常规的测试用例
 * @Remark
 * @Author YINZHIYU
 * @Date 2022/9/26 16:06
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public class GenralTests {
    @Test
    public void helloTest() {
        System.out.println("hello");
    }

    @Test
    public void strategyTest() {
        ProcessStrategy p = new ProcessStrategy();
        p.say(StrategyEnum.A, "你好");
        p.say(StrategyEnum.B, "你好");
        p.say(StrategyEnum.C, "你好");
    }


    @Test
    public void nameTest() throws InterruptedException, ExecutionException {
        List<SysLog> sysLogss = Collections.synchronizedList(new LinkedList<>());
        List<FutureTask<List<SysLog>>> futureTaskList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            FutureTask<List<SysLog>> futureTask = new FutureTask<>(() -> {
                List<SysLog> sysLogs = Lists.newArrayList();
                for (int j = 0; j < 5000; j++) {
                    SysLog sysLog = new SysLog();
                    sysLog.setBusinessKey(Thread.currentThread().getName() + "A" + j);
                    sysLog.setBusinessType(Thread.currentThread().getName() + "B" + j);
                    sysLogs.add(sysLog);
                }
                return sysLogs;
            });
            ThreadUtil.execAsync(futureTask);
            futureTaskList.add(futureTask);
        }

        for (FutureTask<List<SysLog>> futureTask : futureTaskList) {
            sysLogss.addAll(futureTask.get());//get() 阻塞线程
        }
        Map<String, List<SysLog>> groupMaps = sysLogss.stream().collect(Collectors.groupingBy(sys -> sys.getBusinessKey() + sys.getBusinessType()));
        Console.log(JSONUtil.toJsonStr(groupMaps));
    }
}
