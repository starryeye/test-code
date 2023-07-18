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

    public Order createOrder(LocalDateTime currentDate) {

        LocalTime currentTime = currentDate.toLocalTime();

        if(currentTime.isBefore(SHOP_OPENED_TIME) || currentTime.isAfter(SHOP_CLOSED_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
        }

        return new Order(LocalDateTime.now(), beverages);
    }
}
