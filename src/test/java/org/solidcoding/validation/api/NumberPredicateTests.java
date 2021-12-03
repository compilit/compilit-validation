package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.predicates.NumberPredicate;

import static org.solidcoding.validation.predicates.NumberPredicate.shouldBeANumber;
import static org.solidcoding.validation.predicates.NumberPredicate.beANumberOfLength;

class NumberPredicateTests {

    @Test
    void itShould_integerMatchingRule_shouldReturnTrue() {
        var value = 2;
        var rule0 = DefineThat.it(NumberPredicate.shouldBeANumber(2)).otherWiseReport("failure");
        var rule1 = DefineThat.it(NumberPredicate.shouldBeANumber().between(1).and(5)).otherWiseReport("failure");
        var rule2 = DefineThat.it(beANumberOfLength(1)).otherWiseReport("failure");
        var rule3 = DefineThat.it(shouldBeANumber(that -> that.equals(2))).otherWiseReport("failure");
        var rule4 = DefineThat.it(NumberPredicate.shouldBeANumber().containing(2)).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule4).validate()).isTrue();
    }

    @Test
    void itShould_integerNotMatchingRule_shouldReturnFalse() {
        var value = 2;
        var rule0 = DefineThat.it(NumberPredicate.shouldBeANumber(1)).otherWiseReport("failure");
        var rule1 = DefineThat.it(NumberPredicate.shouldBeANumber().between(5).and(50)).otherWiseReport("failure");
        var rule2 = DefineThat.it(beANumberOfLength(10)).otherWiseReport("failure");
        var rule3 = DefineThat.it(shouldBeANumber(that -> that.equals(5435))).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule0).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule3).validate()).isFalse();
    }

    @Test
    void itShould_extendedTests() {
        var value = 123456789;
        var rule1 = DefineThat.it(NumberPredicate.shouldBeANumber().containing(1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 23, 789)).otherWiseReport("failure");
        var rule2 = DefineThat.it(NumberPredicate.shouldBeANumber().containing(10,
                11,
                22,
                33,
                44,
                55,
                66,
                77,
                88,
                9999)).otherWiseReport("failure");
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule1).validate()).isTrue();
        Assertions.assertThat(Validator.makeSure(value).compliesWith(rule2).validate()).isFalse();
    }
}
