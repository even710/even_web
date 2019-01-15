package com.ssm.ser.util;


import com.ssm.ser.cfg.Cfg;
import com.ssm.ser.cfg.MailCfg;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/16
 */
public class MailUtil {
    /**
     * 获取邮件会话
     *
     * @return
     */
    public Session getSession() {
        Properties prop = new Properties();
        prop.setProperty("mail.host", MailCfg.SMTP_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        return session;
    }

    /**
     * 创建一封正文带图片的邮件
     *
     * @param session
     * @param to
     * @param subject
     * @param content
     * @param imagePath
     * @return
     * @throws Exception
     */
    public MimeMessage createImageMail(Session session, String to, String subject, String content, String
            imagePath) throws
            Exception {
        MimeMessage message = getMessage(session, to, subject);
        String[] path = imagePath.split("/");
        String imageName = "";
        if (path.length > 1)
            imageName = path[path.length - 1];

        // 准备邮件数据
        // 准备邮件正文数据
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");
        // 准备图片数据
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(imagePath));
        image.setDataHandler(dh);
        image.setContentID(imageName);
        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();
        //将创建好的邮件写入到E盘以文件的形式进行保存
        message.writeTo(new FileOutputStream(Cfg.getProjectPath() + File.separator + "mailFile" + File.separator + imageName));
        //返回创建好的邮件
        return message;
    }

    /**
     * 创建简单文本
     *
     * @param session
     * @param to
     * @param subject
     * @param content
     * @return
     */
    public MimeMessage createSimpleMessage(Session session, String to, String subject, String content) throws Exception {
        MimeMessage message = getMessage(session, to, subject);
        message.setContent(content, "text/html;charset=UTF-8");
        return message;
    }

    public static MimeMessage createMixedMail(Session session) throws Exception {
        //创建邮件
        MimeMessage message = new MimeMessage(session);

        //设置邮件的基本信息
        message.setFrom(new InternetAddress("gacl@sohu.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
        message.setSubject("带附件和带图片的的邮件");

        //正文
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("xxx这是女的xxxx<br/><img src='cid:aaa.jpg'>", "text/html;charset=UTF-8");

        //图片
        MimeBodyPart image = new MimeBodyPart();
        image.setDataHandler(new DataHandler(new FileDataSource("src\\3.jpg")));
        image.setContentID("aaa.jpg");

        //附件1
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\4.zip"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());

        //附件2
        MimeBodyPart attach2 = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("src\\波子.zip"));
        attach2.setDataHandler(dh2);
        attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

        //描述关系:正文和图片
        MimeMultipart mp1 = new MimeMultipart();
        mp1.addBodyPart(text);
        mp1.addBodyPart(image);
        mp1.setSubType("related");

        //描述关系:正文和附件
        MimeMultipart mp2 = new MimeMultipart();
        mp2.addBodyPart(attach);
        mp2.addBodyPart(attach2);

        //代表正文的bodypart
        MimeBodyPart content = new MimeBodyPart();
        content.setContent(mp1);
        mp2.addBodyPart(content);
        mp2.setSubType("mixed");

        message.setContent(mp2);
        message.saveChanges();

        message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
        //返回创建好的的邮件
        return message;
    }

    /**
     * 获取邮件对象
     *
     * @param session 邮件会话
     * @param to      收件人
     * @param subject 邮件主题
     * @return
     * @throws Exception
     */
    private MimeMessage getMessage(Session session, String to, String subject) throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(MailCfg.USERNAME));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        //邮件的标题
        message.setSubject(subject);
        //返回创建好的邮件对象
        return message;
    }

    /**
     * 发送邮件
     *
     * @param session 邮件会话
     * @param message 邮件
     * @throws MessagingException
     */
    public void sendMessage(Session session, Message message) throws MessagingException {
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(MailCfg.SMTP_HOST, MailCfg.USERNAME, MailCfg.PASSWORD);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    public static void main(String[] args) {
        try {
            HtmlEmail email = new HtmlEmail();//创建电子邮件对象
            email.setDebug(true);
//            email.setHostName("SMTP.qq.com");//设置发送电子邮件使用的服务器主机名
            email.setHostName(MailCfg.SMTP_HOST);//设置发送电子邮件使用的服务器主机名
            email.setSmtpPort(Integer.parseInt(String.valueOf(MailCfg.SMTP_PORT)));//设置发送电子邮件使用的邮件服务器的TCP端口地址
            email.setAuthenticator(new DefaultAuthenticator(MailCfg.USERNAME, MailCfg.PASSWORD));//邮件服务器身份验证
            email.setFrom(MailCfg.USERNAME);//设置发信人邮箱
            email.setSubject("一腔诗意喂了狗");//设置邮件主题
            email.setMsg("this is a test mali with attch");//设置邮件文本内容
            email.setMsg("this is mail with test1");
            email.addTo("2042725139@qq.com");//设置收件人
            email.send();//发送邮件
            email.send();//发送邮件
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
