package com.ssm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.bean.User;
import com.ssm.service.UserService;
import com.ssm.service.UserService1;
import com.ssm.utils.Md5Util;
import com.ssm.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/18
 */
@Controller
public class LoginController {
    @Resource
    private UserService userService;

    //登录认证
    @RequestMapping("login")
    public ModelAndView loginUser(User user) {
        ModelAndView mv = new ModelAndView();

        System.out.println("userName:" + user.getUsername() + ",password:" + user.getPassword());
        if (userService.loginUser(user)) {
            mv.addObject("userName", user.getUsername());
            /*跳转到Demo页面*/
            mv.setViewName("Demo");
        } else
            /*重定向到主页面*/
            mv.setViewName("redirect:/");
        return mv;
    }

    @Reference(check = false)
    private UserService1 userService1;

    private Md5Util md5Util = new Md5Util();

    //登录认证
    @RequestMapping("shiro-login")
    public ModelAndView login(User user) {
        ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            //执行认证操作.
            subject.login(token);
        } catch (AuthenticationException ae) {
            System.out.println("登陆失败: " + ae.getMessage());
            mv.setViewName("redirect:/");
            return mv;
        }
        mv.setViewName("Demo");
        return mv;
    }

    @RequestMapping("shiro-auth")
    public void autho() {
        Subject subject = SecurityUtils.getSubject();
        subject.isPermitted("select");
        subject.hasRole("admin");
    }

    /**
     * 注册
     *
     * @param userVO
     * @return
     */
    @RequestMapping(value = "shiro-register", method = RequestMethod.POST)
    public ModelAndView register(UserVO userVO) {
        ModelAndView mv = new ModelAndView();
//        if (userService.isExitUser(userVO.getUser())) {
//            mv.setViewName("redirect:/");
//            return mv;
//        }
        User user = new User();
        user.setPassword(userVO.getPassword());
        user.setUsername(userVO.getUser());
        md5Util.encryptPassword(user);
        userService.insertUser(user);
        mv.setViewName("redirect:/");
        return mv;
    }

}
