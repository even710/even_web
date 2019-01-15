package com.ssm.ser;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/16
 */

import com.ssm.api.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMailTest() throws Exception {
//        mailService.sendSimpleMail("2042725139@qq.com", "dubbo 测试发送邮件", "我是要成为海贼王的男人");
    }
}
