package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.solidcoding.validation.predicates.DecimalNumberPredicate.beADecimalNumber;

class DoublePredicateTests {

    @Test
    void itShould_doubleMatchingRule_shouldReturnTrue() {
        var value = .2;
        var rule0 = DefineThat.itShould(beADecimalNumber(0.2)).otherWiseReport("I am error");
        var rule1 = DefineThat.itShould(beADecimalNumber().between(.0).and(.5)).otherWiseReport("I am error");
        var rule3 = DefineThat.itShould(beADecimalNumber(that -> that.equals(0.2))).otherWiseReport("I am error");
        var rule4 = DefineThat.itShould(beADecimalNumber().containing(2, 0)).otherWiseReport("I am error");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule4).validate()).isTrue();
    }

    @Test
    void itShould_doubleNotMatchingRule_shouldReturnFalse() {
        var value = .2;
        var rule0 = DefineThat.itShould(beADecimalNumber(1)).otherWiseReport("I am error");
        var rule1 = DefineThat.itShould(beADecimalNumber().between(5).and(.55)).otherWiseReport("I am error");
        var rule3 = DefineThat.itShould(beADecimalNumber(that -> that.equals(.5435))).otherWiseReport("I am error");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isFalse();
    }

    @Test
    void itShould_extendedTests() {
        var value = .123456789;
        var rule1 = DefineThat.itShould(beADecimalNumber().containing(1,
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
        var rule2 = DefineThat.itShould(beADecimalNumber().containing(10,
                11,
                22,
                33,
                44,
                55,
                66,
                77,
                88,
                9999)).otherWiseReport("I am error");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
    }
}
