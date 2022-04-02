package com.compilit.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static com.compilit.validation.api.Definitions.defineThatIt;
import static com.compilit.validation.api.Verifications.verifyThat;
import static com.compilit.validation.predicates.StringPredicate.*;
import static testutil.TestValue.TEST_CONTENT;

class StringPredicateBuilderTests {

  @Test
  void validate_stringMatchingRule_shouldReturnTrue() {
    var value = TEST_CONTENT;
    var rule0 = defineThatIt(isEqualTo(TEST_CONTENT)).otherwiseReport("failure");
    var rule1 = defineThatIt(contains(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
    var rule2 = defineThatIt(doesNotContain("?")).otherwiseReport("failure");
    var rule3 = defineThatIt(hasALengthOf(TEST_CONTENT.length()).and(contains("t"))).otherwiseReport("failure");
    var rule4 = defineThatIt(hasALengthBetween(0).and(10)).otherwiseReport("failure");
    var rule5 = defineThatIt(isAlphabetic()).otherwiseReport("failure");
    var rule6 = defineThatIt(isNotNumeric()).otherwiseReport("failure");
    var rule7 = defineThatIt(isNotNull()).otherwiseReport("failure");
    var rule8 = defineThatIt(isNotNullEmptyOrBlank()).otherwiseReport("failure");
    var rule9 = defineThatIt(isNullEmptyOrBlank()).otherwiseReport("failure");
    Assertions.assertThat(verifyThat(value).compliesWith(rule0).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule1).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule2).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule3).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule4).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule5).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule6).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule7).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule8).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule8).validate()).isTrue();
    Assertions.assertThat(verifyThat(value).compliesWith(rule8).validate()).isTrue();
    Assertions.assertThat(verifyThat((String) null).compliesWith(rule9).validate()).isTrue();
    Assertions.assertThat(verifyThat("").compliesWith(rule9).validate()).isTrue();
    Assertions.assertThat(verifyThat(" ").compliesWith(rule9).validate()).isTrue();
  }

  @Test
  void validate_stringNotMatchingRule_shouldReturnFalse() {
    var value = TEST_CONTENT;
    var rule0 = defineThatIt(isEqualTo("?")).otherwiseReport("failure");
    var rule1 = defineThatIt(contains("?", "bla")).otherwiseReport("failure");
    var rule2 = defineThatIt(doesNotContain(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
    var rule3 = defineThatIt(hasALengthOf(123).and(contains("t"))).otherwiseReport("failure");
    var rule4 = defineThatIt(hasALengthBetween(100).and(200)).otherwiseReport("failure");
    var rule5 = defineThatIt(isNotAlphabetic()).otherwiseReport("failure");
    var rule6 = defineThatIt(isNumeric()).otherwiseReport("failure");
    var rule7 = defineThatIt(isNotNull()).otherwiseReport("failure");
    var rule8 = defineThatIt(isNotNullEmptyOrBlank()).otherwiseReport("failure");
    var rule9 = defineThatIt(isNullEmptyOrBlank()).otherwiseReport("failure");
    Assertions.assertThat(verifyThat(value).compliesWith(rule0).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule1).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule2).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule3).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule4).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule5).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule6).validate()).isFalse();
    Assertions.assertThat(verifyThat((String) null).compliesWith(rule7).validate()).isFalse();
    Assertions.assertThat(verifyThat((String) null).compliesWith(rule8).validate()).isFalse();
    Assertions.assertThat(verifyThat("").compliesWith(rule8).validate()).isFalse();
    Assertions.assertThat(verifyThat(" ").compliesWith(rule8).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule9).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule9).validate()).isFalse();
    Assertions.assertThat(verifyThat(value).compliesWith(rule9).validate()).isFalse();
  }

  @Test
  void andThen_stringMatchingRule_shouldReturnWantedObject() {
    var value = TEST_CONTENT;
    var rule0 = defineThatIt(isEqualTo("test")).otherwiseReport("failure");
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(verifyThat(value).compliesWith(rule0).andThen(supplier).orElseThrow(RuntimeException::new)).isEqualTo(value);
  }

  @Test
  void andThen_stringNotMatchingRule_shouldReturnOtherObject() {
    var value = TEST_CONTENT;
    var otherValue = TEST_CONTENT + TEST_CONTENT;
    var rule0 = defineThatIt(isEqualTo("blah")).otherwiseReport("failure");
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(verifyThat(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isEqualTo(otherValue);
    Assertions.assertThat(verifyThat(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isNotEqualTo(value);
  }

}
