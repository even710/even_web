package com.ssm.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/20
 */
public class MyAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //获取操作主题
        Subject subject = getSubject(request, response);
        //获取配置文件中的权限列表
        String[] permsList = (String[]) mappedValue;

        boolean isPermitted = true;
        if (permsList == null || permsList.length == 0) {
            return isPermitted;
        }
        //检查其权限
        for (String perms : permsList) {
            //只要满足一个权限信息,就返回true
            if (subject.isPermitted(perms)) {
                return isPermitted;
            }
        }

        return false;
    }
}
