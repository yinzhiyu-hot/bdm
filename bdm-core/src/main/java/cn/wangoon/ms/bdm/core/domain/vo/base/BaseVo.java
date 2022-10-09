package cn.wangoon.ms.bdm.core.domain.vo.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseVo {
    private static final long serialVersionUID = 1L;

    protected Long id;

    protected Long createById;

    protected String createByName;

    protected LocalDateTime createTime;

    protected Long modifyById;

    protected String modifyByName;

    protected LocalDateTime modifyTime;
}
