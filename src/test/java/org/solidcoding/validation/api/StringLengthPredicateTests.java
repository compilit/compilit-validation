package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.solidcoding.validation.predicates.StringPredicate.beAString;
import static testutil.TestValue.TEST_CONTENT;

class StringLengthPredicateTests {

    public static Stream<Arguments> validTestCases() {
        return Stream.of(
                Arguments.arguments(0, 5),
                Arguments.arguments(5, 0),
                Arguments.arguments(1, 5),
                Arguments.arguments(5, 1),
                Arguments.arguments(2, 5),
                Arguments.arguments(5, 2),
                Arguments.arguments(3, 5),
                Arguments.arguments(5, 3),
                Arguments.arguments(4, 5),
                Arguments.arguments(5, 4),
                Arguments.arguments(0, 6),
                Arguments.arguments(6, 0),
                Arguments.arguments(0, 7),
                Arguments.arguments(7, 0),
                Arguments.arguments(0, 8),
                Arguments.arguments(8, 0)
        );
    }

    public static Stream<Arguments> invalidTestCases() {
        return Stream.of(
                Arguments.arguments(10, 5),
                Arguments.arguments(5, 10),
                Arguments.arguments(5, 5),
                Arguments.arguments(0, 0),
                Arguments.arguments(1, 1),
                Arguments.arguments(10, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("validTestCases")
    void and_stringMatchingRule_shouldReturnTrue(int first, int second) {
        var rule0 = DefineThat.itShould(beAString().withLengthBetween(first).and(second)).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule0).validate()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidTestCases")
    void and_stringNotMatchingRule_shouldReturnFalse(int first, int second) {
        var rule0 = DefineThat.itShould(beAString().withLengthBetween(first).and(second)).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(TEST_CONTENT).compliesWith(rule0).validate()).isFalse();
    }

}
