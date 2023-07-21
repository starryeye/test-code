package dev.practice.cafekiosk.spring.api.service.product.response;

import dev.practice.cafekiosk.spring.domain.product.Product;
import dev.practice.cafekiosk.spring.domain.product.ProductSellingType;
import dev.practice.cafekiosk.spring.domain.product.ProductType;

public record ProductResponse(
        Long id,
        String productNumber,
        ProductType type,
        ProductSellingType sellingType,
        String name,
        int price
) {

    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductNumber(),
                product.getType(),
                product.getSellingType(),
                product.getName(),
                product.getPrice()
        );
    }
}
