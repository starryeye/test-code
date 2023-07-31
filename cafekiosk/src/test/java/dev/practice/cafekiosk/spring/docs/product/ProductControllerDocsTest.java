package dev.practice.cafekiosk.spring.docs.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.practice.cafekiosk.spring.api.controller.product.ProductController;
import dev.practice.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import dev.practice.cafekiosk.spring.api.service.product.ProductService;
import dev.practice.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import dev.practice.cafekiosk.spring.api.service.product.response.ProductResponse;
import dev.practice.cafekiosk.spring.docs.RestDocsSupport;
import dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus;
import dev.practice.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = Mockito.mock(ProductService.class);

    /**
     * Mocking 된 ProductService 를 가진 ProductController 를 반환한다.
     */
    @Override
    protected Object initController() {
        return new ProductController(productService);
    }


    /**
     * ProductControllerTest 와 기본적으로 구조는 같다.
     * 코드상 stubbing 을 추가로 해줘야 응답을 반환해줄 수 있으므로 stubbing 을 해줬다.
     * MockMvcRestDocumentation 을 통해서 문서를 만드는데 필요한 id, 요청응답 데이터 정의를 해준다.
     *
     * Gradle > documentation > asciidoctor 실행
     */
    @DisplayName("신규 상품을 등록하는 API")
    @Test
    void createProduct() throws Exception {

        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // BDD Stubbing
        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
                .willReturn(
                        ProductResponse.builder()
                                .id(1L)
                                .productNumber("001")
                                .type(ProductType.HANDMADE)
                                .sellingStatus(ProductSellingStatus.SELLING)
                                .name("아메리카노")
                                .price(4000)
                                .build()
                );

        // when // then
        mockMvc.perform(post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                // 여기서부터 RestDocs 를 위한 코드이다.
                //product-create : 문서의 id (임의로 지정하면 된다.)
                //PayloadDocumentation : 요청과 응답 데이터를 정의해준다.
                .andDo(MockMvcRestDocumentation.document("product-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("type").type(JsonFieldType.STRING)
                                        .description("상품 타입"),
                                PayloadDocumentation.fieldWithPath("sellingStatus").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("상품 판매상태"),
                                PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("상품 이름"),
                                PayloadDocumentation.fieldWithPath("price").type(JsonFieldType.NUMBER)
                                        .description("상품 가격")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                PayloadDocumentation.fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                        .description("상품 ID"),
                                PayloadDocumentation.fieldWithPath("data.productNumber").type(JsonFieldType.STRING)
                                        .description("상품 번호"),
                                PayloadDocumentation.fieldWithPath("data.type").type(JsonFieldType.STRING)
                                        .description("상품 타입"),
                                PayloadDocumentation.fieldWithPath("data.sellingStatus").type(JsonFieldType.STRING)
                                        .description("상품 판매상태"),
                                PayloadDocumentation.fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("상품 이름"),
                                PayloadDocumentation.fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                                        .description("상품 가격")
                        )
                ));
    }
}
