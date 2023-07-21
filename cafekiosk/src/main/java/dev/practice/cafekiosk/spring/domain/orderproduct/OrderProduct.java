package dev.practice.cafekiosk.spring.domain.orderproduct;

import dev.practice.cafekiosk.spring.domain.BaseEntity;
import dev.practice.cafekiosk.spring.domain.order.Order;
import dev.practice.cafekiosk.spring.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order; // order 는 어떤 product 를 가지고 있는지 알아야할 경우가 많아서 양방향으로 한다.

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product; // product 는 어떤 order 에 속해있는지 알 필요가 없으므로 단방향으로 한다.

    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
