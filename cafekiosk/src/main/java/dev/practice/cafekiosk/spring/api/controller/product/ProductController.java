package dev.practice.cafekiosk.spring.api.controller.product;

import dev.practice.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import dev.practice.cafekiosk.spring.api.service.product.ProductService;
import dev.practice.cafekiosk.spring.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public void createProduct(ProductCreateRequest productCreateRequest) {
        productService.createProduct(productCreateRequest);
    }

    @GetMapping("/api/v1/products/selling")
    public List<ProductResponse> getProductsSelling() {
        return productService.getProductSelling();
    }
}
