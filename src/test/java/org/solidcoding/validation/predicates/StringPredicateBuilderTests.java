package org.solidcoding.validation.predicates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.Definitions;
import org.solidcoding.validation.api.Verifications;

import java.util.function.Supplier;

import static org.solidcoding.validation.predicates.StringPredicate.*;
import static testutil.TestValue.TEST_CONTENT;

class StringPredicateBuilderTests {

  @Test
  void validate_stringMatchingRule_shouldReturnTrue() {
    var value = TEST_CONTENT;
    var rule0 = Definitions.defineThatIt(isEqualTo(TEST_CONTENT)).otherwiseReport("failure");
    var rule1 = Definitions.defineThatIt(contains(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
    var rule2 = Definitions.defineThatIt(doesNotContain("?")).otherwiseReport("failure");
    var rule3 = Definitions.defineThatIt(hasALengthOf(TEST_CONTENT.length()).and(contains("t"))).otherwiseReport("failure");
    var rule4 = Definitions.defineThatIt(hasALengthBetween(0).and(10)).otherwiseReport("failure");
    var rule5 = Definitions.defineThatIt(isAlphabetic()).otherwiseReport("failure");
    var rule6 = Definitions.defineThatIt(isNotNumeric()).otherwiseReport("failure");
    var rule7 = Definitions.defineThatIt(isNotNull()).otherwiseReport("failure");
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
    var rule0 = Definitions.defineThatIt(isEqualTo("?")).otherwiseReport("failure");
    var rule1 = Definitions.defineThatIt(contains("?", "bla")).otherwiseReport("failure");
    var rule2 = Definitions.defineThatIt(doesNotContain(TEST_CONTENT, TEST_CONTENT)).otherwiseReport("failure");
    var rule3 = Definitions.defineThatIt(hasALengthOf(123).and(contains("t"))).otherwiseReport("failure");
    var rule4 = Definitions.defineThatIt(hasALengthBetween(100).and(200)).otherwiseReport("failure");
    var rule5 = Definitions.defineThatIt(isNotAlphabetic()).otherwiseReport("failure");
    var rule6 = Definitions.defineThatIt(isNumeric()).otherwiseReport("failure");
    var rule7 = Definitions.defineThatIt(isNotNull()).otherwiseReport("failure");
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
    var rule0 = Definitions.defineThatIt(isEqualTo("test")).otherwiseReport("failure");
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).andThen(supplier).orElseThrow(RuntimeException::new)).isEqualTo(value);
  }

  @Test
  void andThen_stringNotMatchingRule_shouldReturnOtherObject() {
    var value = TEST_CONTENT;
    var otherValue = TEST_CONTENT + TEST_CONTENT;
    var rule0 = Definitions.defineThatIt(isEqualTo("blah")).otherwiseReport("failure");
    Supplier<String> supplier = () -> value;
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isEqualTo(otherValue);
    Assertions.assertThat(Verifications.verifyThat(value).compliesWith(rule0).andThen(supplier).orElseReturn(otherValue)).isNotEqualTo(value);
  }

}
