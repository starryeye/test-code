package dev.practice.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    //해피 케이스
    //해피 케이스 빈 리스트
    //예외 케이스 X/O/X
    //예외 케이스 /OX/

    @DisplayName("")
    @Test
    void test() {

        // given
        // when
        // then
    }

}