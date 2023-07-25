package dev.practice.cafekiosk.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

        log.info("메일 전송 실패");

        //외부 시스템 장애임에도 테스트는 성공해야한다.
        throw new RuntimeException("메일 전송 실패");
    }
}
