package dev.practice.cafekiosk.unit.beverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();

//        assertEquals("아메리카노", americano.getName()); // assertEquals 는 JUnit5 에서 제공하는 메서드
        assertThat(americano.getName()).isEqualTo("아메리카노"); // assertThat 은 AssertJ 에서 제공하는 메서드
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}