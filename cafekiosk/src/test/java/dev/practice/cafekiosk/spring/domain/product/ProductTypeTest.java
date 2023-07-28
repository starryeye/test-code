package dev.practice.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    //재고 차감이 필요한 상품 타입인지 체크
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.")
    @Test
    void containsStockType() {

        // given
        ProductType givenType1 = ProductType.HANDMADE;
        ProductType givenType2 = ProductType.BOTTLE;
        ProductType givenType3 = ProductType.BAKERY;

        // when
        boolean result1 = ProductType.containsStockType(givenType1);
        boolean result2 = ProductType.containsStockType(givenType2);
        boolean result3 = ProductType.containsStockType(givenType3);

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    /**
     * csv 는 콤마를 기준으로 split 하여 구분하는 것
     * 따라서, HANDMADE,false 는 아래 테스트 메서드의 각각 파라미터로 넣어진다.
     * @ParameterizedTest
     * 하나의 테스트 케이스에서 여러 값으로 돌아가며 테스트하고 싶을때 사용한다.
     */
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.")
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    @ParameterizedTest
    void containStockType2(ProductType productType, boolean expected) {

        //when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);

    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }

    /**
     * @MethodSource 로 @ParameterizedTest 수행
     */
    @DisplayName("상품 타입이 재고 관련 타입인지 확인한다.")
    @MethodSource(value = "provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containStockType3(ProductType productType, boolean expected) {

        //when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);

    }

}