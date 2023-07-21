package dev.practice.cafekiosk.spring.domain.product;

import dev.practice.cafekiosk.spring.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder // @Builder 를 적용할때는 생성자의 접근지정자를 private 로 하자. 생성자는 외부에서 차단하고 빌더로만 생성 가능하도록
    private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }
}
