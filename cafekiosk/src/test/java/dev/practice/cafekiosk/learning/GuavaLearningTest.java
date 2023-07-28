package dev.practice.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuavaLearningTest {
    /**
     * Guava 라이브러리를 사용하여 Test 코드를 작성하며 학습한다.
     * Test 를 학습의 도구로 사용한다.
     */

    @DisplayName("주어진 갯수만큼 List 를 파티셔닝 한다.")
    @Test
    void partitionLearningTest() {

        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 3);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(
                        List.of(
                            List.of(1, 2, 3),
                            List.of(4, 5, 6)
                        )
                );
    }

    @DisplayName("주어진 갯수만큼 List 를 파티셔닝 한다.")
    @Test
    void partitionLearningTest2() {

        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 4);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(
                        List.of(
                                List.of(1, 2, 3, 4),
                                List.of(5, 6)
                        )
                );
    }

    @DisplayName("멀티맵 기능 확인")
    @Test
    void multimapLearningTest() {

        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("자동차", "G70");
        multimap.put("자동차", "G80");
        multimap.put("자동차", "G90");
        multimap.put("기차", "KTX");
        multimap.put("기차", "SRT");

        // when
        Collection<String> strings = multimap.get("자동차");

        // then
        assertThat(strings).hasSize(3)
                .isEqualTo(
                        List.of("G70", "G80", "G90")
                );
    }

    @DisplayName("멀티맵 기능 확인")
    @TestFactory
    Collection<DynamicTest> multimapLearningTest2() {

        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("자동차", "G70");
        multimap.put("자동차", "G80");
        multimap.put("자동차", "G90");
        multimap.put("기차", "KTX");
        multimap.put("기차", "SRT");

        return List.of(
                DynamicTest.dynamicTest("한개의 value 삭제", () -> {
                    // when
                    multimap.remove("자동차", "G70");

                    // then
                    Collection<String> strings = multimap.get("자동차");
                    assertThat(strings).hasSize(2)
                            .isEqualTo(
                                    List.of("G80", "G90")
                            );
                }),
                DynamicTest.dynamicTest("한개의 key 삭제", () -> {
                    // when
                    multimap.removeAll("자동차");

                    // then
                    Collection<String> strings = multimap.get("자동차");
                    assertThat(strings).isEmpty();
                })
        );
    }
}
