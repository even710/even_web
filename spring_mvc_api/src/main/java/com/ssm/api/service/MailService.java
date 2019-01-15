package com.ssm.api.service;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/16
 */
public interface MailService {
    String sendMail(String from,String to,String subject,String content);
}
//    void sendSimpleMail(String to, String subject, String content) throws Exception;
