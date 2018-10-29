package com.zhw.blog.config;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InterceptorConfig implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);
    public static final String SESSION_KEY = "userName";

    /**
     * 进入controller层之前拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        log.info("拦截请求地址：" + request.getRequestURL());
        boolean flag;
        HttpSession session = request.getSession();
        if (StringUtils.isNotEmpty(ObjectUtils.identityToString(session.getAttribute(SESSION_KEY)))) {
            flag = true;
        } else {
            flag = false;
            response.sendRedirect(request.getContextPath() + "/login");
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------视图渲染之后的操作-------------------------0");
    }
}

