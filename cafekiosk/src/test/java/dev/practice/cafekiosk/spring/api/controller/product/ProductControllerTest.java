package dev.practice.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.practice.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import dev.practice.cafekiosk.spring.api.service.product.ProductService;
import dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus;
import dev.practice.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @WebMvcTest 는 Controller 영역 관련 bean 들만 등록한 container 와 MockMvc 빈을 제공한다.
 */
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Service 쪽 빈들은 등록되지 않고 해당 빈은 Mock 으로 대체하여 진행한다.
     * Mockito 라이브러리
     *
     * 아래 createProduct 테스트 메서드에서 @MockBean 객체가 사용되지 않는 것 처럼 보이지만
     * @MockBean 객체가 없으면 테스트가 수행조차 안된다.
     * @WebMvcTest 의 controller 가 필요로 하는 의존성 객체는
     * @MockBean 으로 등록해줘야하는 것을 알수 있다..
     *
     * 아마 ProductService 의 행동을 정해주지 않으면 null 로 리턴하는듯..
     */
    @MockBean
    private ProductService productService;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {

        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}