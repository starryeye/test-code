package dev.practice.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static dev.practice.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static dev.practice.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring Container 를 띄우고 JPA 를 사용하기 때문에 통합 테스트 이지만..
 * persistence layer 를 테스트하는 것은 DataBase 에 access 하는 로직만 테스트하는 것이므로
 * 단위 테스트 성격에 가깝다.
 *
 * persistence layer 테스트를 하는 이유
 * - 내가 작성한 코드가(JpaRepository 의 쿼리메서드를 사용했다 하더라도)
 * 제대로된 쿼리로 날라가는가를 보장하기 위해 테스트 코드를 해줘야한다.
 * - 추후 다른 기술로 변경될때도 사용하여 기능적으로 동일성을 보장시켜준다.
 */
@ActiveProfiles("test") // profile 지정
@DataJpaTest // JPA 기술 관련 빈들만 주입한 컨테이너를 띄운다. @SpringBootTest 보다 가볍다.
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {

        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(7000)
                .build();
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        /**
         * List 형태의 데이터를 검증할 때는 주로 아래와 같은 형태로 검증한다.
         * - hasSize
         * - extracting : 검증 대상 객체에서 작성한 필드 명의 값들을 빼온다.
         * - containsExactlyInAnyOrder : 빼온 값들이 작성한 값들과 일치하는지 검증한다. (리스트의 순서는 무관)
         */
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {

        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(7000)
                .build();
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        /**
         * List 형태의 데이터를 검증할 때는 주로 아래와 같은 형태로 검증한다.
         * - hasSize
         * - extracting : 검증 대상 객체에서 작성한 필드 명의 값들을 빼온다.
         * - containsExactlyInAnyOrder : 빼온 값들이 작성한 값들과 일치하는지 검증한다. (리스트의 순서는 무관)
         */
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }
}