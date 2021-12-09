package org.solidcoding.validation.newapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BiPredicateRuleTests {

    @Test
    void test_shouldReturnExpectedValue() {
        var actual = new BiPredicateRule<>(String::equals);
        Assertions.assertThat(actual.test("test", "test")).isTrue();
        Assertions.assertThat(actual.test("bla", "test")).isFalse();
    }

    @Test
    void where_shouldReturnExpectedValue() {
        var actual = new BiPredicateRule<>((x, y) -> true).where(Object::equals);
        Assertions.assertThat(actual.test("test", "test")).isTrue();
        Assertions.assertThat(actual.test("bla", "test")).isFalse();
    }
}
