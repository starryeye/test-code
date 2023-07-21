package dev.practice.cafekiosk.spring.domain.product;

import dev.practice.cafekiosk.spring.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(value = EnumType.STRING) // HANDMADE, BOTTLE, BAKERY
    private ProductType type;

    @Enumerated(value = EnumType.STRING) // SELLING, HOLD, STOP_SELLING
    private ProductSellingStatus sellingStatus;

    private String name;
    private int price;
}
