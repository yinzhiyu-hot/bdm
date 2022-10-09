package cn.wangoon.ms.bdm.core.domain.vo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 分页
 * @PackagePath UserInfoEntity
 * @Author YINZHIYU
 * @Date 2020-04-07 09:54:00
 * @Version 1.0.0.0
 **/
@Data
public class BasePageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private long pageNumber = 1;

    /**
     * 码距
     */
    private long pageSize = 20;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 正序/倒序（asc/desc）
     */
    private String sortOrder;
}
