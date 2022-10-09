package cn.wangoon.ms.bdm.core.domain.dto.biz.demo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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
    @NotNull
    @Length(min = 6, max = 20, message = "消息长度需介于6和20之间")
    private String message;
}
