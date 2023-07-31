package dev.practice.cafekiosk.spring.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
//@SpringBootTest
public abstract class RestDocsSupport {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper(); //spring 의존성 없으므로 생성


    /**
     * 이렇게 하면 스프링에서, 스프링을 띄우고 처리하는 것이라.. 통합 테스트 환경에서 측면에서 안좋다...
     */
//    @BeforeEach
//    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
//                .build();
//    }

    /**
     * standaloneSetup 을 이용하면 스프링 의존성 없이 수행가능하다.
     *
     * test 를 수행하여 RestDocs 를 만들 것인데..
     * test 수행 대상 controller 를 MockMvcBuilder 의 standaloneSetup 파라미터로 넘기고 MockMvc 를 생성한다. 생성할때는 RestDocumentation 설정을 같이 넣어준다.
     * -> @WebMvcTest, @Autowired MockMvc 작업인듯?
     */
    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .build();
    }

    protected abstract Object initController();
}
