package dev.practice.cafekiosk.spring.api.service.order;

import dev.practice.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dev.practice.cafekiosk.spring.api.service.order.response.OrderResponse;
import dev.practice.cafekiosk.spring.domain.order.OrderRepository;
import dev.practice.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import dev.practice.cafekiosk.spring.domain.product.Product;
import dev.practice.cafekiosk.spring.domain.product.ProductRepository;
import dev.practice.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dev.practice.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest // @DataJpaTest 는 Jpa 관련 빈만 등록된다. @Service 적용된 빈은 등록되지 않음
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

    //TODO, deleteAll() 과 차이점
    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();

    }

    @DisplayName("주문번호 리스트를 받아서 주문을 생성한다.")
    @Test
    void createOrder() {

        // given
        LocalDateTime registeredAt = LocalDateTime.now();

        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", HANDMADE, 3000);
        Product product3 = createProduct("003", HANDMADE, 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredAt);

        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting("totalPrice", "registeredAt")
                .contains(4000, registeredAt);
        assertThat(orderResponse.products()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("002", 3000)
                );

    }

    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    @Test
    void createOrderWithDuplicateProductNumbers() {

        // given
        LocalDateTime registeredAt = LocalDateTime.now();

        Product product1 = createProduct("001", HANDMADE, 1000);
        Product product2 = createProduct("002", HANDMADE, 3000);
        Product product3 = createProduct("003", HANDMADE, 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredAt);

        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting("totalPrice", "registeredAt")
                .contains(2000, registeredAt);
        assertThat(orderResponse.products()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000)
                );
    }

    // Product 를 생성하는 코드가 너무 길어서 테스트를 위해 넣을 가변적인 필드를 파라미터로 받고 Product 를 생성하는 메서드
    private Product createProduct(String productNumber, ProductType type, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }
}