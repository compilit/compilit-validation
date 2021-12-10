package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.solidcoding.validation.api.contracts.Rule;

import java.util.ArrayList;

class ContinuingPredicateValidatorTests {

  @Test
  void getMessage_noValidation_shouldReturnDefaultMessage() {
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    Assertions.assertThat(predicate.getMessage()).isEqualTo(ContinuingRuleValidationBuilder.DEFAULT_MESSAGE);
  }

  @Test
  void getMessage_valid_shouldReturnDefaultMessage() {
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    Assertions.assertThat(predicate.getMessage()).isEqualTo(ContinuingRuleValidationBuilder.DEFAULT_MESSAGE);
    predicate.validate();
    Assertions.assertThat(predicate.getMessage()).isEqualTo(ContinuingRuleValidationBuilder.DEFAULT_MESSAGE);
  }

  @Test
  void getMessage_invalid_shouldReturnFailMessage() {
    var message = "failure";
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    Assertions.assertThat(predicate.getMessage()).isEqualTo(ContinuingRuleValidationBuilder.DEFAULT_MESSAGE);
    predicate.validate();
    Assertions.assertThat(predicate.getMessage()).contains(message);
  }

  @Test
  void and_shouldAddRule() {
    var value = "test";
    var message1 = "failure1";
    var message2 = "failure2";
    var rules = new ArrayList<Rule<String>>();
    var rule1 = new RuleDefinition<String>(x -> false, message1);
    var rule2 = new RuleDefinition<String>(x -> false, message2);
    rules.add(rule1);
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    predicate.and(rule2);
    predicate.validate();
    Assertions.assertThat(predicate.getMessage()).contains(message1);
    Assertions.assertThat(predicate.getMessage()).contains(message2);
  }

  @Test
  void validate_shouldValidateRules() {
    var value = "test";
    var message1 = "failure1";
    var message2 = "failure2";
    var rules = new ArrayList<Rule<String>>();
    var rule1 = new RuleDefinition<String>(x -> true, message1);
    var rule2 = new RuleDefinition<String>(x -> false, message2);
    rules.add(rule1);
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    predicate.and(rule2);
    predicate.validate();
    Assertions.assertThat(predicate.getMessage()).doesNotContain(message1);
    Assertions.assertThat(predicate.getMessage()).contains(message2);
  }

  @Test
  void andThen_invalid_shouldReturnFalse() {
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    Assertions.assertThat(predicate.andThen(() -> false).orElseReturn(true)).isTrue();
  }

  @Test
  void orElseThrow_valid_shouldReturnTrue() {
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    Assertions.assertThat(predicate.andThen(() -> true).orElseThrow(RuntimeException::new)).isTrue();
  }

  @Test
  void orElseThrow_invalid_shouldThrowException() {
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "failure"));
    var validation = new ContinuingRuleValidationBuilder<>(rules, value).andThen(() -> false);
    Assertions.assertThatThrownBy(() -> validation.orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
  }

  @Test
  void orElseReturn_valid_shouldReturnOriginalValue() {
    var value = true;
    var rules = new ArrayList<Rule<Boolean>>();
    rules.add(new RuleDefinition<>(x -> false, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    Assertions.assertThat(predicate.andThen(() -> true).orElseReturn(x -> false)).isFalse();
  }

  @Test
  void orElseReturn_invalid_shouldReturnOtherValue() {
    var value = true;
    var rules = new ArrayList<Rule<Boolean>>();
    rules.add(new RuleDefinition<>(x -> false, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    predicate.andThen(() -> false).orElseReturn(x -> false);
    Assertions.assertThat(predicate.validate()).isFalse();
  }

  @Test
  void orElseReturn_invalidValidation_shouldReturnOther() {
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    predicate.andThen(() -> true).orElseReturn(false);
    Assertions.assertThat(predicate.validate()).isFalse();
  }

  @Test
  void orElseReturn_validValidation_shouldReturnOriginal() {
    var value = "test";
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "failure"));
    var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
    predicate.andThen(() -> true).orElseReturn(false);
    Assertions.assertThat(predicate.validate()).isTrue();
  }
}
