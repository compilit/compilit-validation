package org.solidcoding.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.Definitions;
import org.solidcoding.validation.api.Verifications;

import static org.solidcoding.validation.predicates.NumberPredicate.contains;
import static org.solidcoding.validation.predicates.NumberPredicate.doesNotContain;
import static org.solidcoding.validation.predicates.NumberPredicate.hasAmountOfDigits;
import static org.solidcoding.validation.predicates.NumberPredicate.isBetween;
import static org.solidcoding.validation.predicates.NumberPredicate.isEqualTo;
import static org.solidcoding.validation.predicates.NumberPredicate.isNotNull;

class NumberPredicateBuilderTests {

  @Test
  void validate_integerMatchingRule_shouldReturnTrue() {
    var value = 2;
    var rule0 = Definitions.defineThatIt(isEqualTo(2)).otherwiseReport("failure");
    var rule1 = Definitions.defineThatIt(isBetween(1).and(5)).otherwiseReport("failure");
    var rule2 = Definitions.defineThatIt(isBetween(5).and(1)).otherwiseReport("failure");
    var rule3 = Definitions.defineThatIt(hasAmountOfDigits(1)).otherwiseReport("failure");
    var rule4 = Definitions.defineThatIt(contains(2, 2)).otherwiseReport("failure");
    var rule5 = Definitions.defineThatIt(doesNotContain(43, 123)).otherwiseReport("failure");
    var rule6 = Definitions.defineThatIt(isNotNull()).otherwiseReport("failure");
    var rule7 = Definitions.defineThatIt(isEqualTo(2).and(isEqualTo(2).and(isEqualTo(2)))).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule2).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule3).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule4).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule5).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule6).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule7).validate()).isTrue();
  }

  @Test
  void validate_integerNotMatchingRule_shouldReturnFalse() {
    var value = 2;
    var rule0 = Definitions.defineThatIt(isEqualTo(1)).otherwiseReport("failure");
    var rule1 = Definitions.defineThatIt(isBetween(5).and(50)).otherwiseReport("failure");
    var rule2 = Definitions.defineThatIt(isBetween(50).and(5)).otherwiseReport("failure");
    var rule3 = Definitions.defineThatIt(hasAmountOfDigits(10)).otherwiseReport("failure");
    var rule4 = Definitions.defineThatIt(isEqualTo(5435)).otherwiseReport("failure");
    var rule5 = Definitions.defineThatIt(doesNotContain(2, 2)).otherwiseReport("failure");
    var rule6 = Definitions.defineThatIt(isNotNull()).otherwiseReport("failure");
    var rule7 = Definitions.defineThatIt(isEqualTo(2).and(isEqualTo(2).and(isEqualTo(1)))).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule1).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule2).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule3).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule4).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule5).validate()).isFalse();
    Assertions.assertThat(Verifications.<Integer>verifyThat(null).compliesWith(rule6).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule7).validate()).isFalse();
  }

  @Test
  void validate_extendedTests() {
    var value = 123456789;
    var rule1 = Definitions.defineThatIt(contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 23, 789)).otherwiseReport("failure");
    var rule2 = Definitions.defineThatIt(contains(10,
            11,
            22,
            33,
            44,
            55,
            66,
            77,
            88,
            9999)).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule2).validate()).isFalse();
  }
}
