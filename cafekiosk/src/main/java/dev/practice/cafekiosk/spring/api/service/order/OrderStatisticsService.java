package dev.practice.cafekiosk.spring.api.service.order;

import dev.practice.cafekiosk.spring.api.service.mail.MailService;
import dev.practice.cafekiosk.spring.domain.order.Order;
import dev.practice.cafekiosk.spring.domain.order.OrderRepository;
import dev.practice.cafekiosk.spring.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public void sendOrderStatisticsMail(LocalDate orderDate, String email) {

        // 해당 일자에 결제완료된 주문들을 가져온다.
        List<Order> orders = orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                OrderStatus.PAYMENT_COMPLETED
        );

        // 가져온 주문들의 총 매출 합계를 계산한다.
        int totalPrice = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        // 메일 전송
        boolean result = mailService.sendMail(
                "no-reply@practice.dev",
                email,
                String.format("[매출 통계] %s", orderDate),
                String.format("총 매출 합계는 %s 입니다.", totalPrice)
        );

        if(!result) {
            throw new RuntimeException("매출 통계 메일 전송에 실패했습니다.");
        }
    }
}
