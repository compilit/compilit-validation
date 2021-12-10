package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import testutil.AbstractTestWithContext;

class VoidEndingRuleValidationBuilderTests extends AbstractTestWithContext {

  private static final String FAIL_MESSAGE = "failure";

  @BeforeEach
  public void reset() {
    super.reset();
  }

  @Test
  void orElseThrow_valid_shouldReturnVoid() {
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(true);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    Assertions.assertThat(validator.orElseThrow(RuntimeException::new)).isNull();
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void orElseThrow_invalid_shouldReturnVoid() {
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(false);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    Assertions.assertThatThrownBy(() -> validator.orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
    Assertions.assertThat(hasBeenInteractedWith()).isFalse();
  }

  @Test
  void orElseLogInfo_validInput_shouldNotLog() {
    var logger = Mockito.mock(Logger.class);
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(true);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    validator.orElseLogInfo(logger);
    Mockito.verifyNoInteractions(logger);
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void orElseLogInfo_invalidInput_shouldLog() {
    var logger = Mockito.mock(Logger.class);
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(false);
    Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    validator.orElseLogInfo(logger);
    Mockito.verify(logger).info(FAIL_MESSAGE);
    Assertions.assertThat(hasBeenInteractedWith()).isFalse();
  }

  @Test
  void orElseLogWarn_validInput_shouldNotLog() {
    var logger = Mockito.mock(Logger.class);
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(true);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    validator.orElseLogWarn(logger);
    Mockito.verifyNoInteractions(logger);
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void orElseLogWarn_invalidInput_shouldLog() {
    var logger = Mockito.mock(Logger.class);
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(false);
    Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    validator.orElseLogWarn(logger);
    Mockito.verify(logger).warn(FAIL_MESSAGE);
    Assertions.assertThat(hasBeenInteractedWith()).isFalse();
  }

  @Test
  void orElseLogError_validInput_shouldNotLog() {
    var logger = Mockito.mock(Logger.class);
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(true);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    validator.orElseLogError(logger);
    Mockito.verifyNoInteractions(logger);
    Assertions.assertThat(hasBeenInteractedWith()).isTrue();
  }

  @Test
  void orElseLogError_invalidInput_shouldLog() {
    var logger = Mockito.mock(Logger.class);
    var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
    Mockito.when(continuingValidator.validate()).thenReturn(false);
    Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
    var validator = new VoidEndingRuleValidatorBuilder<String>(super::interact, continuingValidator);
    validator.orElseLogError(logger);
    Mockito.verify(logger).error(FAIL_MESSAGE);
    Assertions.assertThat(hasBeenInteractedWith()).isFalse();
  }
}