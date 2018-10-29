package com.zhw.blog.controller;

import com.zhw.blog.config.InterceptorConfig;
import com.zhw.blog.model.User;
import com.zhw.blog.service.UserService;
import com.zhw.blog.util.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @RequestMapping(value = "/manage")
    public ModelAndView manage() {
        ModelAndView mv = new ModelAndView("manage");
        return mv;
    }

    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/doLogin")
    public Map doLogin(HttpServletRequest request, HttpSession session) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        boolean flag = false;
        if (StringUtils.isNotEmpty(userName) || StringUtils.isNotEmpty(password)) {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            flag = userService.examineLogin(user);
        }
        if (flag) {
            session.setAttribute(InterceptorConfig.SESSION_KEY, userName);
            return ResponseUtils.writeSuccess("manage");
        } else {
            return ResponseUtils.writeError("用户名或密码错误！");
        }
    }

    @RequestMapping(value = "/loginOut")
    public Integer loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(InterceptorConfig.SESSION_KEY) != null) {
            session.removeAttribute(InterceptorConfig.SESSION_KEY);
            return 1;
        } else {
            return 0;
        }
    }
}
