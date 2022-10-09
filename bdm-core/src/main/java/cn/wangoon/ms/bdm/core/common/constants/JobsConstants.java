package cn.wangoon.ms.bdm.core.common.constants;

/**
 * @Description Job 用到的常量
 * @Remark
 * @PackagePath cn.wangoon.constants.JobsConstants
 * @Author YINZHIYU
 * @Date 2021/3/12 12:01
 * @Version 1.0.0.0
 **/
public interface JobsConstants {

    Integer RETRY_COUNT = 2;//重试次数

    /**
     * 默认表达式，默认十分钟跑一次
     */
    String DEFAULT_CRON_EXPRESSION = "0 */10 * * * ?";
}
