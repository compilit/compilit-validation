package org.solidcoding.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.Define;
import org.solidcoding.validation.api.MakeSure;

import static org.solidcoding.validation.predicates.DecimalNumberPredicateBuilder.*;

class DecimalNumberPredicateBuilderTests {

    @Test
    void validate_doubleMatchingRule_shouldReturnTrue() {
        var value = .2;
        var rule0 = Define.thatIt(is(0.2)).otherWiseReport("I am error");
        var rule1 = Define.thatIt(is().between(.0).and(.5)).otherWiseReport("I am error");
        var rule3 = Define.thatIt(is(that -> that.equals(0.2))).otherWiseReport("I am error");
        var rule4 = Define.thatIt(contains(0, 2)).otherWiseReport("I am error");
        var rule5 = Define.thatIt(contains(2)).otherWiseReport("I am error");
        var rule6 = Define.thatIt(doesNotContain(4, 5643)).otherWiseReport("I am error");
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule0).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule3).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule4).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule5).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule6).validate()).isTrue();
    }

    @Test
    void validate_doubleNotMatchingRule_shouldReturnFalse() {
        var value = .2;
        var rule0 = Define.thatIt(is(1)).otherWiseReport("I am error");
        var rule1 = Define.thatIt(is().between(5).and(.55)).otherWiseReport("I am error");
        var rule3 = Define.thatIt(is(that -> that.equals(.5435))).otherWiseReport("I am error");
        var rule4 = Define.thatIt(contains(54)).otherWiseReport("I am error");
        var rule5 = Define.thatIt(doesNotContain(2)).otherWiseReport("I am error");
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule0).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule1).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule3).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule4).validate()).isFalse();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule5).validate()).isFalse();
    }

    @Test
    void validate_extendedTests() {
        var value = .123456789;
        var rule1 = Define.thatIt(contains(1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                12,
                23,
                789)).otherWiseReport("I am error");
        var rule2 = Define.thatIt(contains(10,
                11,
                22,
                33,
                44,
                55,
                66,
                77,
                88,
                9999)).otherWiseReport("I am error");
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(MakeSure.that(value).compliesWith(rule2).validate()).isFalse();
    }
}
