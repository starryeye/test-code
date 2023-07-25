package dev.practice.cafekiosk.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

        log.info("메일 전송 완료");

        return true;
    }
}
