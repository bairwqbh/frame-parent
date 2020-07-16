package per.cby.frame.message.email.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import per.cby.frame.message.email.EmailService;

/**
 * 邮件发送服务实现
 * 
 * @author chenboyang
 * @since 2019年6月4日
 *
 */
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false)
    private MailProperties mailProperties;

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Override
    public void send(String subject, String content, String[] to, String[] cc, String[] bcc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(content);
        message.setTo(to);
        message.setCc(bcc);
        message.setBcc(bcc);
        message.setSentDate(new Date());
        message.setFrom(mailProperties.getUsername());
        javaMailSender.send(message);
    }

}
