package dev.practice.cafekiosk.spring.api.service.order.response;

import dev.practice.cafekiosk.spring.api.service.product.response.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        int totalPrice,
        LocalDateTime registeredAt,
        List<ProductResponse> products
) {
}
