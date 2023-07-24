package dev.practice.cafekiosk.spring.api.service.product;

import dev.practice.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
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

    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {

        String nextProductNumber = createNextProductNumber();

        return ProductResponse.builder()
                .productNumber(nextProductNumber)
                .type(productCreateRequest.getType())
                .sellingStatus(productCreateRequest.getSellingStatus())
                .name(productCreateRequest.getName())
                .price(productCreateRequest.getPrice())
                .build();
    }

    private String createNextProductNumber() {
        // productNumber 채번, DB 에서 가장 마지막 productNumber 를 조회하여 +1 해준다.

        String latestProductNumber = productRepository.findLatestProductNumber();
        int latestProductNumberInt = Integer.parseInt(latestProductNumber);

        int nextProductNumber = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumber);
    }

    public List<ProductResponse> getProductSelling() {

        List<Product> products = productRepository.findBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
