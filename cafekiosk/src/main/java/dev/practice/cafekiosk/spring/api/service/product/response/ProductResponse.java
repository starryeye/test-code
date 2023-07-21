package dev.practice.cafekiosk.spring.api.service.product.response;

import dev.practice.cafekiosk.spring.domain.product.Product;
import dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus;
import dev.practice.cafekiosk.spring.domain.product.ProductType;

public record ProductResponse(
        Long id,
        String productNumber,
        ProductType type,
        ProductSellingStatus sellingStatus,
        String name,
        int price
) {

    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductNumber(),
                product.getType(),
                product.getSellingStatus(),
                product.getName(),
                product.getPrice()
        );
    }
}
