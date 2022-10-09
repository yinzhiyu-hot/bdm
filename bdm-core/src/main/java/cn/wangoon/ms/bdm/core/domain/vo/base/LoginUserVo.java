package cn.wangoon.ms.bdm.core.domain.vo.base;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description 登录VO
 * @Remark
 * @Author YINZHIYU
 * @Date 2020/8/24 11:41
 * @Version 1.0.0.0
 **/
@Data
public class LoginUserVo implements Serializable {
    @NotBlank(message = "username is not empty")
    private String username;
    @NotBlank(message = "password is not empty")
    private String password;
}
