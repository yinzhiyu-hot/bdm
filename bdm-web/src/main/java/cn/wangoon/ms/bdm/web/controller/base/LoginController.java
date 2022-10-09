package cn.wangoon.ms.bdm.web.controller.base;

import cn.hutool.core.util.ObjectUtil;
import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.core.common.constants.RedisConstants;
import cn.wangoon.ms.bdm.core.common.utils.RedisUtils;
import cn.wangoon.ms.bdm.core.common.utils.StringUtils;
import cn.wangoon.ms.bdm.core.domain.vo.base.LoginUserVo;
import cn.wangoon.ms.bdm.web.config.LoginConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @Description 登录
 * @Remark
 * @Author YINZHIYU
 * @Date 2020/8/24 11:39
 * @Version 1.0.0.0
 **/
@Api(tags = "登录")
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    public RedisUtils redisUtils;

    @Resource
    private LoginConfig loginConfig;

    /**
     * @Description 登录页面
     * @Remark
     * @Params ==>
     * @Return java.lang.String
     * @Date 2020/8/24 13:30
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "GET")
    @RequestMapping(value = "/")
    public String login() {
        return "login";
    }

    /**
     * @Description 系统后台管理中心登录
     * @Remark
     * @Params ==>
     * @Param loginUserVO
     * @Param request
     * @Param response
     * @Return cn.domain.common.Result<java.lang.String>
     * @Date 2022-03-24 14:03:29
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "系统后台管理中心登录", notes = "系统后台管理中心登录", httpMethod = "POST")
    @PostMapping("/loginIn")
    @ResponseBody
    public Result loginIn(@RequestBody LoginUserVo loginUserVO, HttpServletRequest request, HttpServletResponse response) {

        String requestURI = request.getRequestURI();

        //取登录信息
        Object session;
        try {
            session = redisUtils.get(String.format("%s%s%s", RedisConstants.SYS_PREFIX, RedisConstants.LOGIN_REDIS, StringUtils.base64Encode(String.format("%s-%s", loginConfig.username, loginConfig.password))));
        } catch (Exception e) {
            return Result.failure("登录失败,操作Redis 异常");
        }

        //如果登录成功过，直接跳转到访问页面
        if (ObjectUtil.isNotEmpty(session)) {
            return Result.success("登录成功");
        }
        if (Objects.equals(loginUserVO.getUsername(), loginConfig.username) && Objects.equals(loginUserVO.getPassword(), loginConfig.password)) {
            redisUtils.set(String.format("%s%s%s", RedisConstants.SYS_PREFIX, RedisConstants.LOGIN_REDIS, StringUtils.base64Encode(String.format("%s-%s", loginConfig.username, loginConfig.password))), StringUtils.base64Encode(String.format("%s-%s", loginConfig.username, loginConfig.password)), 60 * 30);
            return Result.success("登录成功");
        }
        return Result.failure("登录失败");
    }
}
