package dev.practice.cafekiosk.unit.order;

import dev.practice.cafekiosk.unit.beverage.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Order {

    private final LocalDateTime orderedAt;
    private final List<Beverage> beverages;
}
