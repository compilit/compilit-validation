package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
        var failMessage = "failure";
        var value = "test";
        var rules = new ArrayList<Rule<String>>();
        rules.add(new RuleDefinition<>(x -> false, "failure"));
        var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
        Assertions.assertThat(predicate.getMessage()).isEqualTo(ContinuingRuleValidationBuilder.DEFAULT_MESSAGE);
        predicate.validate();
        Assertions.assertThat(predicate.getMessage()).contains(failMessage);
    }

    @Test
    void and_shouldAddRule() {
        var value = "test";
        var failMessage1 = "failure1";
        var failMessage2 = "failure2";
        var rules = new ArrayList<Rule<String>>();
        var rule1 = new RuleDefinition<String>(x -> false, failMessage1);
        var rule2 = new RuleDefinition<String>(x -> false, failMessage2);
        rules.add(rule1);
        var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
        predicate.and(rule2);
        predicate.validate();
        Assertions.assertThat(predicate.getMessage()).contains(failMessage1);
        Assertions.assertThat(predicate.getMessage()).contains(failMessage2);
    }

    @Test
    void validate_shouldValidateRules() {
        var value = "test";
        var failMessage1 = "failure1";
        var failMessage2 = "failure2";
        var rules = new ArrayList<Rule<String>>();
        var rule1 = new RuleDefinition<String>(x -> true, failMessage1);
        var rule2 = new RuleDefinition<String>(x -> false, failMessage2);
        rules.add(rule1);
        var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
        predicate.and(rule2);
        predicate.validate();
        Assertions.assertThat(predicate.getMessage()).doesNotContain(failMessage1);
        Assertions.assertThat(predicate.getMessage()).contains(failMessage2);
    }

    @Test
    void andThen_valid_shouldCallAddedSupplier() {
        var value = "test";
        var rules = new ArrayList<Rule<String>>();
        rules.add(new RuleDefinition<>(x -> true, "failure"));
        var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
        predicate.andThen(() -> true).orElseReturn(false);
        Assertions.assertThat(predicate.validate()).isTrue();
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
        var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
        Assertions.assertThatThrownBy(() -> predicate.andThen(() -> false).orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
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
        rules.add(new RuleDefinition<>(x -> true, "failure"));
        var predicate = new ContinuingRuleValidationBuilder<>(rules, value);
        predicate.andThen(() -> true).orElseReturn(false);
        Assertions.assertThat(predicate.validate()).isTrue();
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
