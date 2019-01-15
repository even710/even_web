package com.ssm.ser.service;

import com.ssm.api.service.MailService;
import org.springframework.stereotype.Service;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/16
 */
@Service("mailService")
public class MailServiceImpl implements MailService {
    @Override
    public String sendMail(String from, String to, String subject, String content) {
        return "send mail from " + from + " to " + to + "\n" +
                "主题：" + subject + "\n" + "内容：" + content;
    }
//    @Override
//    public void sendSimpleMail(String to, String subject, String content) throws Exception {
//        MailUtil mailUtil = new MailUtil();
//        Session session = mailUtil.getSession();
//        MimeMessage message = mailUtil.createSimpleMessage(session, to, subject, content);
//        mailUtil.sendMessage(session, message);
//    }
}
