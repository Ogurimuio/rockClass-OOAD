package com.example.rockclass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender jms;

    @Value("${spring.mail.username}")
    private String sender;

    public void sentEmail(String receiverEmail,String content)throws Exception{
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者

        mainMessage.setFrom(sender);
        //接收者
        mainMessage.setTo(receiverEmail);
        //发送的标题
        mainMessage.setSubject("忘记密码");
        //发送的内容
        mainMessage.setText("您的密码是："+content);
        try {
            jms.send(mainMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
