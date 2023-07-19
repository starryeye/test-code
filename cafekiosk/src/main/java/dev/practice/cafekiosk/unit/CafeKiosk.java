package dev.practice.cafekiosk.unit;

import dev.practice.cafekiosk.unit.beverage.Beverage;
import dev.practice.cafekiosk.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private static final LocalTime SHOP_OPENED_TIME = LocalTime.of(10, 0);
    private static final LocalTime SHOP_CLOSED_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {

        if(count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문 가능합니다.");
        }

        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {

        return beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    public Order createOrder() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalTime currentTime = currentDate.toLocalTime();

        if(currentTime.isBefore(SHOP_OPENED_TIME) || currentTime.isAfter(SHOP_CLOSED_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
        }

        return new Order(LocalDateTime.now(), beverages);
    }

    /**
     * 테스트하기 어려운 영역을 구분하고 분리할 줄 알아야한다.
     * - 테스트 하기 쉬운 코드에 테스트가 어려운 코드가 생기면 테스트 쉬웠던 전체가 테스트하기 어려워진다.
     * - 테스트 어려운 코드를 외부로 분리해보자.. (createOrder 메서드의 현재 시간을 파라미터로 받는 조치를 취함)
     * - 가만히 생각해보면 우리가 테스트하려고 하는 영역은 현재 시간이 아니라 어떤 시간이 주어졌을때 우리가 개발한 로직으로 주문 생성을 수행하느냐 이다.
     * - 테스트 어려운 코드를 계속 외부로 분리하면 테스트 가능한 코드는 많아진다.. (현재시간을 받는 부분을 계속 콜 스택 타고 올린다..)
     * - 어디까지 외부 세계로 넘겨야하나...? 더이상 넘길수 없을 때까지...
     *
     * 테스트 하기 어려운 영역 (Adapter)
     * 테스트하기 쉬운 영역 (Application)
     */
    public Order createOrder(LocalDateTime currentDate) {

        LocalTime currentTime = currentDate.toLocalTime();

        if(currentTime.isBefore(SHOP_OPENED_TIME) || currentTime.isAfter(SHOP_CLOSED_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
        }

        return new Order(LocalDateTime.now(), beverages);
    }
}
