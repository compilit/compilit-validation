package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.solidcoding.validation.api.contracts.Rule;
import testutil.AbstractTestWithContext;
import testutil.TestObject;

import java.util.ArrayList;

import static org.solidcoding.validation.predicates.ObjectPredicate.isA;

class VoidReturningRuleValidationBuilderTests extends AbstractTestWithContext {

  private static final String FAIL_MESSAGE = "failure";

  @BeforeEach
  public void reset() {
    super.reset();
  }

  @Test
  void orElseThrow_valid_shouldReturnVoid() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "fail"));
    var validator = new VoidRuleValidatorBuilder<>(rules, "test", super::interact);
    Assertions.assertThat(validator.orElseThrow(RuntimeException::new)).isNull();
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void orElseThrow_invalid_shouldReturnVoid() {
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, "fail"));
    var validator = new VoidRuleValidatorBuilder<>(rules, "test", super::interact);
    Assertions.assertThatThrownBy(() -> validator.orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
    Assertions.assertThat(hasBeenInteractedWith()).isFalse();
  }

  @Test
  void orElseLogMessage_validInput_shouldNotLog() {
    var logger = Mockito.mock(Logger.class);
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> true, "fail"));
    var validator = new VoidRuleValidatorBuilder<>(rules, "test", super::interact);
    Verifications.verifyThat("test").compliesWith(rules).andThen(super::interact).orElseLogMessage();
    validator.orElseLogMessage(logger);
    Mockito.verifyNoInteractions(logger);
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void orElseLogMessage_invalidInput_shouldLog() {
    var logger = Mockito.mock(Logger.class);
    var rules = new ArrayList<Rule<String>>();
    rules.add(new RuleDefinition<>(x -> false, FAIL_MESSAGE));
    var validator = new VoidRuleValidatorBuilder<>(rules, "test", super::interact);
    validator.orElseLogMessage(logger);
    Mockito.verify(logger).error(Mockito.anyString());
    Assertions.assertThat(hasBeenInteractedWith()).isFalse();
  }
}