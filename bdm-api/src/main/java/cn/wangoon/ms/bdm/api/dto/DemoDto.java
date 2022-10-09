package cn.wangoon.ms.bdm.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description demo
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-08 11:20:27
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoDto implements Serializable {
    String message;
}
