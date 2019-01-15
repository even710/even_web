package com.ssm.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/9/14
 */
public class BaseUrlServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        String contextPath = req.getServletContext().getContextPath();
        int port = req.getServerPort();

        //网站的访问跟路径
        String baseURL = scheme + "://" + serverName + ":" + port
                + contextPath;
        req.setAttribute("baseURL", baseURL);
        System.out.println("baseURL:" + baseURL);
        super.service(req, res);
    }
}
