package cn.wangoon.ms.bdm.web.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.wangoon.ms.bdm.core.common.constants.BdmConstants;
import cn.wangoon.ms.bdm.core.common.constants.RedisConstants;
import cn.wangoon.ms.bdm.core.common.utils.RedisUtils;
import cn.wangoon.ms.bdm.core.common.utils.StringUtils;
import cn.wangoon.ms.bdm.web.config.LoginConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Description 简易的登录拦截[生产环境才进行url拦截登录验证]
 * @Author YINZHIYU
 * @Date 2020/5/29 9:26
 * @Version 1.0.0.0
 **/
@Component
public class LoginContextInterceptor extends HandlerInterceptorAdapter {

    @Resource
    public RedisUtils redisUtils;

    @Resource
    public LoginConfig loginConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        //开启登录验证，且访问bdm-job系统后台管理中心，进行session校验
        if (loginConfig.enable && loginConfig.urls.contains(requestURI)) {

            Object session = redisUtils.get(String.format("%s%s%s", RedisConstants.SYS_PREFIX, RedisConstants.LOGIN_REDIS, StringUtils.base64Encode(String.format("%s-%s", loginConfig.username, loginConfig.password))));

            if (ObjectUtil.isEmpty(session)) {
                response.sendRedirect(String.format("%s%s", loginConfig.domain, BdmConstants.SYS_MAMAGER_LOGIN_URL));
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }
}
