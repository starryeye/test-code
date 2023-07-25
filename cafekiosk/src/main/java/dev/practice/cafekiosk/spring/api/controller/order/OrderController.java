package dev.practice.cafekiosk.spring.api.controller.order;

import dev.practice.cafekiosk.spring.api.ApiResponse;
import dev.practice.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dev.practice.cafekiosk.spring.api.service.order.OrderService;
import dev.practice.cafekiosk.spring.api.service.order.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        LocalDateTime registeredAt = LocalDateTime.now();
        return ApiResponse.ok(orderService.createOrder(orderCreateRequest.toServiceRequest(), registeredAt));
    }
}
