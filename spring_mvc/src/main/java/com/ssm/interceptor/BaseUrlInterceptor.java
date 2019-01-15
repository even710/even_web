package com.ssm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/14
 */
public class BaseUrlInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String scheme = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();
        String contextPath = httpServletRequest.getContextPath();
        int port = httpServletRequest.getServerPort();

        //网站的访问跟路径
        String baseURL = scheme + "://" + serverName + ":" + port
                + contextPath;
        httpServletRequest.setAttribute("baseURL", baseURL);
        System.out.println("baseURL:" + baseURL);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
