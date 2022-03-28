package com.compilit.validation.predicates;

import com.compilit.validation.api.Definitions;
import com.compilit.validation.api.Verifications;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static testutil.TestValue.TEST_CONTENT;

class StringPredicateBuilderTests {

  @Test
  void validate_stringMatchingRule_shouldReturnTrue() {
    var value = TEST_CONTENT;
    var rule0 = Definitions.defineThatIt(StringPredicate.isEqualTo(TEST_CONTENT)).otherwiseReport("failure");
    var rule1 = Definitions.defineThatIt(StringPredicate.contains(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
    var rule2 = Definitions.defineThatIt(StringPredicate.doesNotContain("?")).otherwiseReport("failure");
    var rule3 = Definitions.defineThatIt(StringPredicate.hasALengthOf(TEST_CONTENT.length()).and(StringPredicate.contains("t"))).otherwiseReport("failure");
    var rule4 = Definitions.defineThatIt(StringPredicate.hasALengthBetween(0).and(10)).otherwiseReport("failure");
    var rule5 = Definitions.defineThatIt(StringPredicate.isAlphabetic()).otherwiseReport("failure");
    var rule6 = Definitions.defineThatIt(StringPredicate.isNotNumeric()).otherwiseReport("failure");
    var rule7 = Definitions.defineThatIt(StringPredicate.isNotNull()).otherwiseReport("failure");
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
  void validate_stringNotMatchingRule_shouldReturnFalse() {
    var value = TEST_CONTENT;
    var rule0 = Definitions.defineThatIt(StringPredicate.isEqualTo("?")).otherwiseReport("failure");
    var rule1 = Definitions.defineThatIt(StringPredicate.contains("?", "bla")).otherwiseReport("failure");
    var rule2 = Definitions.defineThatIt(StringPredicate.doesNotContain(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
    var rule3 = Definitions.defineThatIt(StringPredicate.hasALengthOf(123).and(StringPredicate.contains("t"))).otherwiseReport("failure");
    var rule4 = Definitions.defineThatIt(StringPredicate.hasALengthBetween(100).and(200)).otherwiseReport("failure");
    var rule5 = Definitions.defineThatIt(StringPredicate.isNotAlphabetic()).otherwiseReport("failure");
    var rule6 = Definitions.defineThatIt(StringPredicate.isNumeric()).otherwiseReport("failure");
    var rule7 = Definitions.defineThatIt(StringPredicate.isNotNull()).otherwiseReport("failure");
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule1).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule2).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule3).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule4).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule5).validate()).isFalse();
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule6).validate()).isFalse();
    Assertions.assertThat(Verifications.<String>verifyThat(null).compliesWith(rule7).validate()).isFalse();
  }

  @Test
  void andThen_stringMatchingRule_shouldReturnWantedObject() {
    var value = TEST_CONTENT;
    var rule0 = Definitions.defineThatIt(StringPredicate.isEqualTo("test")).otherwiseReport("failure");
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).andThen(supplier).orElseThrow(RuntimeException::new)).isEqualTo(value);
  }

  @Test
  void andThen_stringNotMatchingRule_shouldReturnOtherObject() {
    var value = TEST_CONTENT;
    var otherValue = TEST_CONTENT + TEST_CONTENT;
    var rule0 = Definitions.defineThatIt(StringPredicate.isEqualTo("blah")).otherwiseReport("failure");
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isEqualTo(otherValue);
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isNotEqualTo(value);
  }

}
