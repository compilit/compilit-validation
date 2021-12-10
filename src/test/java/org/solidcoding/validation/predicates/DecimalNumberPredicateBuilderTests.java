package org.solidcoding.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.Define;
import org.solidcoding.validation.api.Verify;

import static org.solidcoding.validation.predicates.DecimalNumberPredicate.contains;
import static org.solidcoding.validation.predicates.DecimalNumberPredicate.doesNotContain;
import static org.solidcoding.validation.predicates.DecimalNumberPredicate.isBetween;
import static org.solidcoding.validation.predicates.DecimalNumberPredicate.isEqualTo;
import static org.solidcoding.validation.predicates.DecimalNumberPredicate.isNotNull;

class DecimalNumberPredicateBuilderTests {

    @Test
    void validate_doubleMatchingRule_shouldReturnTrue() {
        var value = .2;
        var rule0 = Define.thatIt(isEqualTo(0.2)).otherwiseReport("I am error");
        var rule1 = Define.thatIt(isBetween(.0).and(.5)).otherwiseReport("I am error");
        var rule2 = Define.thatIt(isBetween(.5).and(.0)).otherwiseReport("I am error");
        var rule3 = Define.thatIt(contains(0, 2)).otherwiseReport("I am error");
        var rule4 = Define.thatIt(contains(2)).otherwiseReport("I am error");
        var rule5 = Define.thatIt(doesNotContain(4, 5643)).otherwiseReport("I am error");
        var rule6 = Define.thatIt(isNotNull()).otherwiseReport("I am error");
        Assertions.assertThat(Verify.that(value).compliesWith(rule0).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule2).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule3).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule4).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule5).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule6).validate()).isTrue();
    }

    @Test
    void validate_doubleNotMatchingRule_shouldReturnFalse() {
        var value = .2;
        var rule0 = Define.thatIt(isEqualTo(1)).otherwiseReport("I am error");
        var rule1 = Define.thatIt(isBetween(5).and(.55)).otherwiseReport("I am error");
        var rule2 = Define.thatIt(isBetween(.55).and(.5)).otherwiseReport("I am error");
        var rule3 = Define.thatIt(contains(54)).otherwiseReport("I am error");
        var rule4 = Define.thatIt(doesNotContain(2)).otherwiseReport("I am error");
        var rule5 = Define.thatIt(isNotNull()).otherwiseReport("I am error");
        Assertions.assertThat(Verify.that(value).compliesWith(rule0).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule1).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule2).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule3).validate()).isFalse();
        Assertions.assertThat(Verify.that(value).compliesWith(rule4).validate()).isFalse();
        Assertions.assertThat(Verify.<Double>that(null).compliesWith(rule5).validate()).isFalse();
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
                789)).otherwiseReport("I am error");
        var rule2 = Define.thatIt(contains(10,
                11,
                22,
                33,
                44,
                55,
                66,
                77,
                88,
                9999)).otherwiseReport("I am error");
        Assertions.assertThat(Verify.that(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Verify.that(value).compliesWith(rule2).validate()).isFalse();
    }
}
