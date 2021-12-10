package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.solidcoding.validation.api.contracts.Rule;

import java.util.ArrayList;

class ReturningRuleValidationBuilderTests {

  private static final String VALUE = "value";
  private static final String OTHER = "other";
  private static final String FAIL_MESSAGE = "failure";

  @Test
  void orElseThrow_validInput_shouldNotThrowExceptionAndReturnTrue() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    Assertions.assertThat(validator.orElseThrow(RuntimeException::new)).isEqualTo(VALUE);
  }

  @Test
  void orElseThrow_invalidInput_shouldThrowGivenAException() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    Assertions.assertThatThrownBy(() -> validator.orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
  }


  @Test
  void orElseReturnValue_validInput_shouldReturnValue() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    Assertions.assertThat(validator.orElseReturn(OTHER)).isEqualTo(VALUE);
  }

  @Test
  void orElseReturnValue_invalidInput_shouldReturnOtherValue() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    Assertions.assertThat(validator.orElseReturn(OTHER)).isEqualTo(OTHER);
  }

  @Test
  void orElseReturnFunction_validInput_shouldReturnValue() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    Assertions.assertThat(validator.orElseReturn(x -> OTHER)).isEqualTo(VALUE);
  }

  @Test
  void orElseReturnFunction_invalidInput_shouldReturnFunction() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    Assertions.assertThat(validator.orElseReturn(x -> OTHER)).isEqualTo(OTHER);
  }

  @Test
  void orElseLogMessage_validInput_shouldNotLog() {
    var logger = Mockito.mock(Logger.class);
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    validator.orElseLogMessage(logger);
    Mockito.verifyNoInteractions(logger);
  }

  @Test
  void orElseLogMessage_invalidInput_shouldLog() {
    var logger = Mockito.mock(Logger.class);
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "fail"));
    var validator = new ReturningRuleValidationBuilder<>(rules, "test", () -> VALUE);
    validator.orElseLogMessage(logger);
    Mockito.verify(logger).error(Mockito.anyString());
  }
}