package org.solidcoding.validation.newapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PredicateRuleTests {

    @Test
    void ctor() {
        var actual = new PredicateRule<String>(x -> x.equals("test"));
        Assertions.assertThat(actual.test("test")).isTrue();
        Assertions.assertThat(actual.test("bla")).isFalse();
    }

    @Test
    void where_shouldReturnExpectedValue() {
        var actual = new PredicateRule<>(x -> true).where(x -> x.equals("test"));
        Assertions.assertThat(actual.test("test")).isTrue();
        Assertions.assertThat(actual.test("bla")).isFalse();
    }
}
