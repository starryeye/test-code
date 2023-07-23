package dev.practice.cafekiosk.spring.api.controller.product.request;

import dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus;
import dev.practice.cafekiosk.spring.domain.product.ProductType;
import lombok.Getter;

@Getter
public class ProductCreateRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;
}
