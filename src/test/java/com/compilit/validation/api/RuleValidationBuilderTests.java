package com.compilit.validation.api;

import com.compilit.validation.api.contracts.ContinuingValidationBuilder;
import com.compilit.validation.api.contracts.Rule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static testutil.TestValue.TEST_CONTENT;

class RuleValidationBuilderTests {

  @Test
  void compliesWith_validInput_shouldReturnValidator() {
    var rule = new RuleDefinition<String>(x -> true, TEST_CONTENT);
    Assertions.assertThat(Verifications.verifyThat(TEST_CONTENT).compliesWith(rule))
            .isInstanceOf(ContinuingValidationBuilder.class);
  }

  @Test
  void compliesWith_invalidInput_shouldReturnValidator() {
    var rule = new RuleDefinition<String>(x -> false, TEST_CONTENT);
    Assertions.assertThat(Verifications.verifyThat(TEST_CONTENT).compliesWith(rule))
            .isInstanceOf(ContinuingValidationBuilder.class);
  }

  @Test
  void compliesWith_multipleRulesWithValidInput_shouldReturnValidator() {
    Rule<String> rule1 = new RuleDefinition<>(x -> true, TEST_CONTENT);
    Rule<String> rule2 = new RuleDefinition<>(x -> true, TEST_CONTENT);
    Rule<String> rule3 = new RuleDefinition<>(x -> true, TEST_CONTENT);
    var rules = List.of(rule1, rule2, rule3);
    Assertions.assertThat(Verifications.verifyThat(TEST_CONTENT).compliesWith(rules))
            .isInstanceOf(ContinuingValidationBuilder.class);
  }

  @Test
  void compliesWith_multipleRulesWithInvalidInput_shouldReturnValidator() {
    Rule<String> rule1 = new RuleDefinition<>(x -> false, TEST_CONTENT);
    Rule<String> rule2 = new RuleDefinition<>(x -> true, TEST_CONTENT);
    Rule<String> rule3 = new RuleDefinition<>(x -> true, TEST_CONTENT);
    var rules = List.of(rule1, rule2, rule3);
    Assertions.assertThat(Verifications.verifyThat(TEST_CONTENT).compliesWith(rules))
            .isInstanceOf(ContinuingValidationBuilder.class);
  }

}