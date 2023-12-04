package dev.practice.cafekiosk.spring.api.service.mail;

import dev.practice.cafekiosk.spring.client.mail.MailSendClient;
import dev.practice.cafekiosk.spring.domain.history.mail.MailSendHistory;
import dev.practice.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring Container 의존성 없이, 순수 Mockito 로 단위 테스트를 수행한다.
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    /**
     * @ExtendWith, @Mock 어노테이션을 활용하면
     * 49, 50 line 처럼 직접 생성해주지 않을 수 있다.
     */
    @Mock
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    /**
     * @ExtendWith, @InjectMocks 어노테이션을 활용하면
     * 52 line 처럼 직접 생성해주지 않을 수 있다. 생성에 필요한 빈들은 생성된 Mock 객체들을 찾아서 DI 해준다.
     */
    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송을 수행한다.")
    @Test
    void sendMail() {

        // given
        /**
         * MailService 에 대한 순수 Mockito 단위 테스트를 위해..
         * - MailService 가 의존하는 빈들을 Mock 으로 생성한다.
         * - MailService 를 생성한다.
         */
//        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
//        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        //stubbing
        Mockito.when(
                mailSendClient.sendMail(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString()
                )
        ).thenReturn(true);

        // Mock 객체의 기본 반환은 null 인듯.. 그래서 아래를 수행하지 않아도 된다.
        // (MailService 에서 save 를 호출하고 반환값으로 무언가 하는 로직이 없기도 하므로..)
        Mockito.when(
                mailSendHistoryRepository.save(
                        Mockito.any(MailSendHistory.class)
                )
        ).thenReturn(null);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        /**
         * verify
         * mailSendHistoryRepository Mock 객체의 save() 메서드 호출 횟수를 검증한다.
         */
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1))
                .save(Mockito.any(MailSendHistory.class));
    }

}