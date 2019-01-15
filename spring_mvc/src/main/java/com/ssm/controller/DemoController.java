package com.ssm.controller;

import com.ssm.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/14
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    /**
     * RequestMapping注解为指定处理对应的url请求
     *
     * @return
     */
    @RequestMapping("person")
    public String toPerson() {
        Subject subject = SecurityUtils.getSubject();
//        subject.hasRole()
//        if (subject.isPermitted("select"))
        return "Demo";
//        else
//            return "/";
    }

    @RequestMapping("/demo")
    public String toDemo() {
        return "Demo";
    }
}
