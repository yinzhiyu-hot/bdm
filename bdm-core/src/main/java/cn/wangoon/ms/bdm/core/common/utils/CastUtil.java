package cn.wangoon.ms.bdm.core.common.utils;

import cn.hutool.core.util.ObjectUtil;

/**
 * @Description 转换工具
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 09:17:25
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@SuppressWarnings("unchecked")
public class CastUtil {

    /**
     * @Description 转换
     * @Params ==>
     * @Param object
     * @Return T
     * @Date 2020/5/18 9:08
     * @Auther YINZHIYU
     */
    public static <T> T cast(Object object) {
        if (ObjectUtil.isEmpty(object)) {
            return null;
        }
        return (T) object;
    }
}
