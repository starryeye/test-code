package dev.practice.cafekiosk.spring.domain.order;

import dev.practice.cafekiosk.spring.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static dev.practice.cafekiosk.spring.domain.order.OrderStatus.INIT;
import static dev.practice.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class OrderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주어진 날짜 범위와 주문 상태 조건으로 주문 엔티티를 조회한다.")
    @Test
    void findOrdersBy() {

        // given
        LocalDateTime now = LocalDateTime.of(2023, 7, 26, 0, 0, 0);

        Order order1 = createOrderWithEmptyProducts(LocalDateTime.of(2023, 7, 25, 23, 59, 59), PAYMENT_COMPLETED);
        Order order2 = createOrderWithEmptyProducts(now, PAYMENT_COMPLETED);
        Order order3 = createOrderWithEmptyProducts(now, INIT);
        Order order4 = createOrderWithEmptyProducts(LocalDateTime.of(2023, 7, 26, 23, 59, 59), PAYMENT_COMPLETED);
        Order order5 = createOrderWithEmptyProducts(LocalDateTime.of(2023, 7, 27, 0, 0, 0), PAYMENT_COMPLETED);
        orderRepository.saveAll(List.of(order1, order2, order3, order4, order5));

        // when
        List<Order> orders = orderRepository.findOrdersBy(
                LocalDateTime.of(2023, 7, 26, 0, 0, 0),
                LocalDateTime.of(2023, 7, 27, 0, 0, 0),
                PAYMENT_COMPLETED
        );

        // then
        assertThat(orders).hasSize(2)
                .extracting("registeredAt", "orderStatus")
                .containsExactlyInAnyOrder(
                        tuple(LocalDateTime.of(2023, 7, 26, 0, 0, 0), PAYMENT_COMPLETED),
                        tuple(LocalDateTime.of(2023, 7, 26, 23, 59, 59), PAYMENT_COMPLETED)
                );

    }

    @DisplayName("데이터가 존재하지 않으면 빈 리스트를 반환한다.")
    @Test
    void findEmptyOrdersBy() {

        // when
        List<Order> orders = orderRepository.findOrdersBy(
                LocalDateTime.of(2023, 7, 26, 0, 0, 0),
                LocalDateTime.of(2023, 7, 27, 0, 0, 0),
                PAYMENT_COMPLETED
        );

        // then
        assertThat(orders).hasSize(0);
    }

    private Order createOrderWithEmptyProducts(LocalDateTime now, OrderStatus orderStatus) {
        return Order.builder()
                .products(List.of())
                .orderStatus(orderStatus)
                .registeredAt(now)
                .build();
    }

}