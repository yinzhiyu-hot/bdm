package cn.wangoon.ms.bdm.core.common.constants;

/**
 * @Description 常量类
 * @Remark
 * @Author YINZHIYU
 * @Date 2022/9/26 16:40
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public interface BdmConstants {
    String LOG = "LOG";

    /**
     * token验证
     */
    String AUTH_TOKEN = "authorization";

    /**
     * 用户信息头
     */
    String USERINFO_HEADER = "x-userinfo-header";

    /**
     * 默认用户
     */
    String ADMIN_USER_NAME = "admin";

    /**
     * 默认仓库编码
     */
    Long WAREHOUSE_ID = 1L;

    /**
     * springboot环境的端口key
     */
    String LOCAL_SERVER_PORT = "local.server.port";

    /**
     * 格式化拼接常量
     */
    String STRING_FORMAT_TWO_CONCAT = "%s_%s";
    String STRING_FORMAT_THREE_CONCAT = "%s_%s_%s";

    /**
     * 后台管理登录拦截界面
     */
    String SYS_MAMAGER_INDEX_URL = "/bdm-web/index/";
    String SYS_MAMAGER_LOGIN_URL = "/bdm-web/login/";

}
