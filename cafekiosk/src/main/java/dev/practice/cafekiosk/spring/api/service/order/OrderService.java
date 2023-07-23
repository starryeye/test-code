package dev.practice.cafekiosk.spring.api.service.order;

import dev.practice.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dev.practice.cafekiosk.spring.api.service.order.response.OrderResponse;
import dev.practice.cafekiosk.spring.domain.order.Order;
import dev.practice.cafekiosk.spring.domain.order.OrderRepository;
import dev.practice.cafekiosk.spring.domain.product.Product;
import dev.practice.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest, LocalDateTime registeredAt) {

        List<String> productNumbers = orderCreateRequest.getProductNumbers();

        //product 조회
        List<Product> products = findByProductsBy(productNumbers);

        //order 생성 및 저장
        Order order = Order.create(products, registeredAt);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findByProductsBy(List<String> productNumbers) {
        //product 조회
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        //중복 허용을 위한 map 생성
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, product -> product));

        //중복 처리된 product
        return productNumbers.stream()
                .map(productMap::get)
                .toList();
    }
}
