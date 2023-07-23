package dev.practice.cafekiosk.spring.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private List<String> productNumbers;

    /**
     * 참고로..
     * Record 는 @Builder 를 canonical constructor 에 적용할 수 있지만,
     * 접근 지정자를 private 로 하지 못한다.
     */
    @Builder
    private OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
