package cn.imhtb.live.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MailUtil {

    @Value("${spring.mail.username}")
    public String USER_NAME;//发送者

    private final JavaMailSender mailSender;

    private Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(6);

    private final AtomicInteger count = new AtomicInteger(1);

    public MailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void start(final JavaMailSender mailSender, final SimpleMailMessage message) {
        service.execute(() -> {
            try {
                if (count.get() == 2) {
                    service.shutdown();
                    logger.info("the task is down");
                }
                logger.info("start send email and the index is " + count);
                mailSender.send(message);
                logger.info("send email success");
            } catch (Exception e) {
                logger.error("send email fail", e);
            }

        });
    }

    public void sendSimpleMessage(String email, String subject, String content) {
        logger.info("发送邮件：{}", content);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USER_NAME);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
