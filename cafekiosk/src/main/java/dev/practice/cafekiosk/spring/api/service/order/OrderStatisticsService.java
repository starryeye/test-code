package dev.practice.cafekiosk.spring.api.service.order;

import dev.practice.cafekiosk.spring.api.service.mail.MailService;
import dev.practice.cafekiosk.spring.domain.order.Order;
import dev.practice.cafekiosk.spring.domain.order.OrderRepository;
import dev.practice.cafekiosk.spring.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 해당 서비스에는 @Transactional 을 적용하지 않는다.
 * - 해당 로직에는 DB 조회, 저장이 존재하는데 Repository 단계에서 각각 @Transactional 이 걸려있어서 JPA 기능에는 문제없고
 * - 변경감지 사용하지 않으며, History DB 에는 단건 저장으로 쓰기 지연 기능 필요 없다.
 * - 해당 서비스에 @Transactional 을 적용하면 메일 전송에 소요되는 시간 까지 포함하여 DB 커넥션 자원을 차지하게 되므로 좋지 못함.
 */
@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {

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
                String.format("총 매출 합계는 %s원 입니다.", totalPrice)
        );

        if(!result) {
            throw new RuntimeException("매출 통계 메일 전송에 실패했습니다.");
        }

        return true;
    }
}
