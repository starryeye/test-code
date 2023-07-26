package dev.practice.cafekiosk.spring.api.service.mail;

import dev.practice.cafekiosk.spring.client.mail.MailSendClient;
import dev.practice.cafekiosk.spring.domain.history.mail.MailSendHistory;
import dev.practice.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring Container 의존성 없이, 순수 Mockito 로 단위 테스트를 수행한다.
 *
 * BDDMockito 행동주도개발
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTestWithBDD {

    @Mock
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송을 수행한다.")
    @Test
    void sendMail() {

        // given

        //stubbing
        //아래 코드는 given 에 위치하지만.. when 절이 있어서 위화감이 든다.
//        Mockito.when(
//                mailSendClient.sendMail(
//                        Mockito.anyString(),
//                        Mockito.anyString(),
//                        Mockito.anyString(),
//                        Mockito.anyString()
//                )
//        ).thenReturn(true);

        //그래서 BDDMockito 를 사용하여 아래와 같이 위화감을 없앴다.
        //BDDMockito 는 Mockito 를 상속 받은 객체이며 모든 동작이 동일하다. 단지 메서드 이름만 바뀐것.
        BDDMockito.given(
                mailSendClient.sendMail(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString()
                )
        ).willReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        Mockito.verify(mailSendHistoryRepository, Mockito.times(1))
                .save(Mockito.any(MailSendHistory.class));
    }

}