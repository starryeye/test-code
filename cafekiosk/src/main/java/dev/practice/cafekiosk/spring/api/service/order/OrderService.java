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

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest, LocalDateTime registeredAt) {

        List<String> productNumbers = orderCreateRequest.getProductNumbers();

        //product 조회
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        //order 생성 및 저장
        Order order = Order.create(products, registeredAt);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }
}
