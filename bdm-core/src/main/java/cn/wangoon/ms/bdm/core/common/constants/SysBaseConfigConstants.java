package cn.wangoon.ms.bdm.core.common.constants;

/**
 * @Description 基础配置常量
 * @Remark
 * @PackagePath cn.wangoon.common.constants.SysBaseConfigConstants
 * @Author Thomas
 * @Date 2021/2/20 17:34
 * @Version 1.0.0.0
 **/
public interface SysBaseConfigConstants {

    /**
     * 重试次数
     */
    String RETRY_COUNT = "RETRY_COUNT";
    String RETRY_COUNT_SYNC_TASK = "RETRY_COUNT_SYNC_TASK";//同步任务重置次数

    /**
     * 前缀
     */
    String PREFIX = "PREFIX";
    String PREFIX_REMOVE = "PREFIX_REMOVE";//前缀移除

    /**
     * 后缀
     */
    String SUFFIX = "SUFFIX";
    String SUFFIX_REMOVE = "SUFFIX_REMOVE";//后缀移除

    /**
     * 日志结转天数
     */
    int LOG_CARRY_FORWARD_DAY_OFFSET = -15;

    /**
     * 同步任务结转天数
     */
    int TASK_CARRY_FORWARD_DAY_OFFSET = -15;

    /**
     * JOB日志结转天数
     */
    int JOB_CARRY_FORWARD_DAY_OFFSET = -2;

    /**
     * 内存阈值(单位M)
     */
    double FREE_MEMORY_DEFAULT_VALUE = 100;
    String FREE_MEMORY = "FREE_MEMORY";
    String FREE_MEMORY_MIN = "FREE_MEMORY_MIN";//内存最小阈值
    String FREE_MEMORY_TURN_ON_OFF = "FREE_MEMORY_TURN_ON_OFF";
    boolean FREE_MEMORY_TURN_ON_OFF_DEFAULT_VALUE = false;

    /**
     * 批次号-业务前缀
     */
    String DEMO = "DEMO";
    String SPVADD = "SPVADD";
    String SPVUPDATE = "SPVUPDATE";
    String PS = "PS";
    String PD = "PD";
    String REVOUT = "REVOUT";
    String PAYMENT = "PAYMENT";
    String SETTLEMENT = "SETTLEMENT";
    String SELLOUT = "SELL_OUT";
    String SALERETURN = "SALE_RETURN";
    String TRANSFERIN = "TRANSFER_IN";
    String INTERNALTRAN = "INTERNALTRAN";
    String INVOICE = "INVOICE";

    /**
     * 平台
     */
    String PLATFORM = "PLATFORM";
    /**
     * 异常日志
     */
    String EXCEPTION_LOG = "EXCEPTION_LOG";

    String ERP_SETTLEMENT_EXCEPTION = "ERP_SETTLEMENT_EXCEPTION";

    /**
     * 存在次序的库存接口调用间隔时间（min）
     */
    String STOCK_DELAY_TIME_MIN = "STOCK_DELAY_TIME_MIN";

    /**
     * 调用接口类型
     */
    String SYNC_TASK_TYPE = "SYNC_TASK_TYPE";

    /**
     * 业务异常类型
     */
    String BUSINESS_EXCEPTION_TYPE = "BUSINESS_EXCEPTION_TYPE";

    /**
     * 平台站点店铺
     */
    String PLATFORM_SITE_SHOP = "PLATFORM_SITE_SHOP";

    String EBAY = "EBAY";

    /**
     * 任务状态
     */
    String SYNC_TASK_STATUS = "SYNC_TASK_STATUS";

    String OCS_PAYMENT_EX_LOG_ADD = "OCS_PAYMENT_EX_LOG_ADD";
    String OCS_SETTLEMENT_EX_LOG_ADD = "OCS_SETTLEMENT_EX_LOG_ADD";

    String Amazon = "Amazon";

}
