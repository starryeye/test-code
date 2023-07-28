package dev.practice.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    //재고가 적으면 트루
    @DisplayName("재고의 수량이 주어진 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan() {

        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when
        boolean result = stock.isQuantityLessThan(quantity);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("재고의 수량을 주어진 갯수만큼 차감할 수 있다.")
    @Test
    void deductQuantity() {

        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우는 예외가 발생한다.")
    @Test
    void deductQuantity2() {

        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when // then
        assertThatThrownBy(
                () -> stock.deductQuantity(quantity)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");
    }


    /**
     * DynamicTest 는 시나리오 테스트를 수행할때 사용하면 좋다.
     * 아래는 기본 구조이다.
     *
     * Disabled 처리
     */
    @Disabled
    @DisplayName("")
    @TestFactory
    Collection<DynamicTest> dynamicTest() {

        return List.of(
                DynamicTest.dynamicTest("", () -> {

                }),
                DynamicTest.dynamicTest("", () -> {

                })
        );
    }

    /**
     * DynamicTest 로 시나리오 테스트를 수행할 수 있다.
     * Stock 변수를 공용자원으로 사용하여 재고 수량을 바꿔가면서 테스트를 하고 있다.
     */
    @DisplayName("재고 차감 시나리오")
    @TestFactory
    Collection<DynamicTest> stockDeductionDynamicTest() {

        // given
        Stock stock = Stock.create("001", 1);

        return List.of(
                DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", () -> {
                    // given
                    int quantity = 1;

                    // when
                    stock.deductQuantity(quantity);

                    // then
                    assertThat(stock.getQuantity()).isZero();
                }),
                DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감 시도하는 경우에는 예외가 발생한다.", () -> {
                    // given
                    int quantity = 1;

                    // when // then
                    assertThatThrownBy(() -> stock.deductQuantity(quantity))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("차감할 재고 수량이 없습니다.");
                })
        );
    }

}