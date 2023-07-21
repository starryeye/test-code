package dev.practice.cafekiosk.spring.api.service.product;

import dev.practice.cafekiosk.spring.api.service.product.response.ProductResponse;
import dev.practice.cafekiosk.spring.domain.product.Product;
import dev.practice.cafekiosk.spring.domain.product.ProductRepository;
import dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getProductSelling() {

        List<Product> products = productRepository.findBySellingTypeIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
