package com.compilit.validation.predicates;

import com.compilit.validation.api.Definitions;
import com.compilit.validation.api.Verifications;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.compilit.validation.api.Definitions.*;
import static com.compilit.validation.api.Verifications.*;
import static com.compilit.validation.predicates.DecimalNumberPredicate.*;
import static com.compilit.validation.predicates.DecimalNumberPredicate.isADecimalNumberBetween;

class DecimalNumberPredicateBuilderTests {

  @Test
  void validate_doubleMatchingRule_shouldReturnTrue() {
    var value = .2;
    var rule0 = defineThatIt(isEqualTo(0.2)).otherwiseReport("I am error");
    var rule1 = defineThatIt(isADecimalNumberBetween(.0).and(.5)).otherwiseReport("I am error");
    var rule2 = defineThatIt(isADecimalNumberBetween(.5).and(.0)).otherwiseReport("I am error");
    var rule3 = defineThatIt(isADecimalNumberContaining(0, 2)).otherwiseReport("I am error");
    var rule4 = defineThatIt(isADecimalNumberContaining(2)).otherwiseReport("I am error");
    var rule5 = defineThatIt(isADecimalNumberNotContaining(4, 5643)).otherwiseReport("I am error");
    var rule6 = defineThatIt(isNotNull()).otherwiseReport("I am error");
    var rule7 = defineThatIt(isNotEqualTo(0.1)).otherwiseReport("I am error");
    Assertions.assertThat(verifyThat(value).compliesWith(rule0).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule2).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule3).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule4).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule5).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule6).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule7).validate()).isTrue();
  }

  @Test
  void validate_doubleNotMatchingRule_shouldReturnFalse() {
    var value = .2;
    var rule0 = defineThatIt(isADecimalNumberEqualTo(1)).otherwiseReport("I am error");
    var rule1 = defineThatIt(isADecimalNumberBetween(5).and(.55)).otherwiseReport("I am error");
    var rule2 = defineThatIt(isADecimalNumberBetween(.55).and(.5)).otherwiseReport("I am error");
    var rule3 = defineThatIt(isADecimalNumberContaining(54)).otherwiseReport("I am error");
    var rule4 = defineThatIt(isADecimalNumberNotContaining(2)).otherwiseReport("I am error");
    var rule5 = defineThatIt(isNotNull()).otherwiseReport("I am error");
    Assertions.assertThat(verifyThat(value).compliesWith(rule0).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule1).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule2).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule3).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule4).validate()).isFalse();
    Assertions.assertThat(Verifications.<Double>verifyThat(null).compliesWith(rule5).validate()).isFalse();
  }

  @Test
  void validate_extendedTests() {
    var value = .123456789;
    var rule1 = defineThatIt(isADecimalNumberContaining(1,
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
    var rule2 = defineThatIt(isADecimalNumberContaining(10,
            11,
            22,
            33,
            44,
            55,
            66,
            77,
            88,
            9999)).otherwiseReport("I am error");
    Assertions.assertThat(verifyThat(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule2).validate()).isFalse();
  }
}
