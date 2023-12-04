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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring Container 의존성 없이, 순수 Mockito 로 단위 테스트를 수행한다.
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTestWithSpy {

    @Spy
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송을 수행한다.")
    @Test
    void sendMail() {

        // given

        // stubbing
//        Mockito.when(
//                mailSendClient.sendMail(
//                        Mockito.anyString(),
//                        Mockito.anyString(),
//                        Mockito.anyString(),
//                        Mockito.anyString()
//                )
//        ).thenReturn(true);
        /**
         * @Spy 어노테이션을 활용하면 @Spy 가 적용된 객체는 기본적으로 실제 객체처럼 동작한다.
         * - 이와는 다르게 @Mock 은 기본적으로.. null 을 반환..
         * - @Mock 객체와 다른 문법을 사용해야한다.
         * - - 그래서 행동을 정해주려면 위 코드처럼 Mockito.when() 을 사용할 수 없고 아래처럼 다른 문법이 존재한다.
         *
         * @Mock 정리
         * mailSendClient 에 @Mock 어노테이션을 적용시키면
         * 기본적으로 Mockito 내부 동작에 따라 기본 값(null)을 리턴해준다. (아무것도 안한다.)
         * 테스트에서 필요에 따라 다른 동작을 수행하기 위해서는 위와 같이 행동 정의를 해줘야한다.
         *
         * @Spy 정리
         * 최종적으로 mailSendClient 는 @Spy 어노테이션이 적용되었기 때문에
         * 기본적으로 실제 객체처럼 동작을 하며...
         * 테스트에서 필요에 따라 다른 동작을 수행하기 위해서는 아래처럼 행동 정의를 해줘야한다.
         * -> 하나의 객체에서 일부 메서드는 실제로 동작했으면 좋겠고.. 일부는 stubbing 을 하고 싶을때..
         */
        Mockito.doReturn(true)
                .when(mailSendClient)
                .sendMail(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()
                );

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1))
                .save(Mockito.any(MailSendHistory.class));
    }

}