package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.Email;
import cn.imhtb.antlive.service.IMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

//@Service
public class MailServiceImpl implements IMailService {

    //执行者

    private final JavaMailSender mailSender;
    //private final Configuration configuration;//freemarker

    //发送者

    @Value("${spring.mail.username}")
    public String USER_NAME;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(Email email) {
//        MailUtils mailUtil = new MailUtils();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(USER_NAME);
//        message.setTo(emial.getEmail());
//        message.setSubject(emial.getSubject());
//        message.setText(emial.getContent());
//        mailUtil.start(mailSender, message);
    }

}
