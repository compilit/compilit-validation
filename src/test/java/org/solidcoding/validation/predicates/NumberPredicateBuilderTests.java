package org.solidcoding.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.Define;
import org.solidcoding.validation.api.MakeSure;

import static org.solidcoding.validation.predicates.NumberPredicateBuilder.*;

class NumberPredicateBuilderTests {

    @Test
    void validate_integerMatchingRule_shouldReturnTrue() {
        var value = 2;
        var rule0 = Define.thatIt(is(2)).otherWiseReport("failure");
        var rule1 = Define.thatIt(is().between(1).and(5)).otherWiseReport("failure");
        var rule2 = Define.thatIt(hasAmountOfDigits(1)).otherWiseReport("failure");
        var rule3 = Define.thatIt(contains(2, 2)).otherWiseReport("failure");
        var rule4 = Define.thatIt(doesNotContain(43, 123)).otherWiseReport("failure");
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule0).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule2).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule3).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule4).validate()).isTrue();
    }

    @Test
    void validate_integerNotMatchingRule_shouldReturnFalse() {
        var value = 2;
        var rule0 = Define.thatIt(is(1)).otherWiseReport("failure");
        var rule1 = Define.thatIt(is().between(5).and(50)).otherWiseReport("failure");
        var rule2 = Define.thatIt(hasAmountOfDigits(10)).otherWiseReport("failure");
        var rule3 = Define.thatIt(is(5435)).otherWiseReport("failure");
        var rule4 = Define.thatIt(doesNotContain(2, 2)).otherWiseReport("failure");
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule0).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule1).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule2).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule3).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule4).validate()).isFalse();
    }

    @Test
    void validate_extendedTests() {
        var value = 123456789;
        var rule1 = Define.thatIt(contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 23, 789)).otherWiseReport("failure");
        var rule2 = Define.thatIt(contains(10,
                11,
                22,
                33,
                44,
                55,
                66,
                77,
                88,
                9999)).otherWiseReport("failure");
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule2).validate()).isFalse();
    }
}