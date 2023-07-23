package dev.practice.cafekiosk.spring.api.service.order;

import dev.practice.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dev.practice.cafekiosk.spring.api.service.order.response.OrderResponse;
import dev.practice.cafekiosk.spring.domain.order.OrderRepository;
import dev.practice.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import dev.practice.cafekiosk.spring.domain.product.Product;
import dev.practice.cafekiosk.spring.domain.product.ProductRepository;
import dev.practice.cafekiosk.spring.domain.product.ProductType;
import dev.practice.cafekiosk.spring.domain.stock.Stock;
import dev.practice.cafekiosk.spring.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dev.practice.cafekiosk.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderService orderService;

    //TODO, deleteAll() 과 차이점
//    @AfterEach
//    void tearDown() {
//        orderProductRepository.deleteAllInBatch();
//        productRepository.deleteAllInBatch();
//        orderRepository.deleteAllInBatch();
//
//    }

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

    //재고와 관련된 상품이란.. 병음료와 베이커리이다.
    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아서 주문을 생성한다.")
    @Test
    void createOrderWithStock() {

        // given
        LocalDateTime registeredAt = LocalDateTime.now();

        Product product1 = createProduct("001", BOTTLE, 1000);
        Product product2 = createProduct("002", BAKERY, 3000);
        Product product3 = createProduct("003", HANDMADE, 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002", "003"))
                .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest, registeredAt);

        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting("totalPrice", "registeredAt")
                .contains(10000, registeredAt);
        assertThat(orderResponse.products()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("001", 1000),
                        tuple("002", 3000),
                        tuple("003", 5000)
                );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 0),
                        tuple("002", 1)
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