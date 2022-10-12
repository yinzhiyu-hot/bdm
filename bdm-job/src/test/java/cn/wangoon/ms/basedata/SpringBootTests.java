package cn.wangoon.ms.basedata;


import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import cn.wangoon.ms.bdm.core.common.utils.RedisUtils; 
import cn.wangoon.ms.bdm.job.BdmJobApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description 基于SpringBoot 容器管理的测试用例
 * @Remark
 * @Author YINZHIYU
 * @Date 2022/9/26 16:05
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BdmJobApplication.class)
public class SpringBootTests {
    @Resource
    RedisUtils redisUtils;
 
    @Test
    public void helloTest() {
        System.out.println("hello");
    }
 
    @Test
    public void redisTest() {
        redisUtils.set("a", "a");
    }
}
