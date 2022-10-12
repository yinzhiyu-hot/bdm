package cn.wangoon.ms.basedata;

import cn.wangoon.ms.bdm.web.BdmWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description 基于SpringBoot 容器管理的测试用例
 * @Remark
 * @Author YINZHIYU
 * @Date 2022/9/26 16:05
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BdmWebApplication.class)
public class SpringBootTests {
    @Test
    public void helloTest() {
        System.out.println("hello");
    }
}
