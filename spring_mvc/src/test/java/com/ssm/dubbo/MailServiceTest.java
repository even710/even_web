package com.ssm.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ssm.api.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-dubbo.xml"})
public class MailServiceTest {
    @Reference
    private MailService mailService;

    @Test
    public void sendMailTest() throws Exception {
        System.out.println(mailService.sendMail("2042725139@qq.com", "2377077883@qq.com", "dubbo 测试发送邮件", "我是要成为海贼王的男人"));
    }
}
