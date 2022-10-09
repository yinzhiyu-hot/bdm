package cn.wangoon.ms.bdm.core.common.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description Bean 映射
 * @Remark 参考文献 https://mapstruct.org
 * @Author YINZHIYU
 * @Date 2022/4/12 9:45
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Mapper
public interface BeanToBean {
    BeanToBean MAPPER = Mappers.getMapper(BeanToBean.class);
}
