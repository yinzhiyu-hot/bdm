package cn.wangoon.ms.bdm.core.common.enums;

import cn.hutool.core.util.StrUtil;

import java.util.Objects;

/**
 * @Description 逻辑删除标识
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 14:13:43
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public enum DelFlagEnum {
    NO("未删除", 0),
    YES("已删除", 1);

    // 成员变量
    private String remark;

    // 删除标记
    private Integer flag;

    // 构造方法
    DelFlagEnum(String remark, Integer flag) {
        this.remark = remark;
        this.flag = flag;
    }

    public static String getRemark(Integer flag) {
        for (DelFlagEnum delFlagEnum : DelFlagEnum.values()) {
            if (Objects.equals(delFlagEnum.getFlag(), flag)) {
                return delFlagEnum.getRemark();
            }
        }
        return StrUtil.EMPTY;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
