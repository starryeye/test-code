package dev.practice.cafekiosk.spring.api.service.order.response;

import dev.practice.cafekiosk.spring.api.service.product.response.ProductResponse;
import dev.practice.cafekiosk.spring.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        int totalPrice,
        LocalDateTime registeredAt,
        List<ProductResponse> products
) {

    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalPrice(),
                order.getRegisteredAt(),
                order.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                        .toList()
        );
    }
}
