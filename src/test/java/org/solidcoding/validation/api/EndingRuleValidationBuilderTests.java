package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

class EndingRuleValidationBuilderTests {

    private static final String VALUE = "value";
    private static final String OTHER = "other";
    private static final String FAIL_MESSAGE = "failure";

    @Test
    void orElseThrow_validInput_shouldNotThrowExceptionAndReturnTrue() {
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        Assertions.assertThat(validator.orElseThrow(RuntimeException::new)).isEqualTo(VALUE);
    }

    @Test
    void orElseThrow_invalidInput_shouldThrowGivenAException() {
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        Assertions.assertThatThrownBy(() -> validator.orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
    }


    @Test
    void orElseReturnValue_validInput_shouldReturnValue() {
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        Assertions.assertThat(validator.orElseReturn(OTHER)).isEqualTo(VALUE);
    }

    @Test
    void orElseReturnValue_invalidInput_shouldReturnOtherValue() {
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        Assertions.assertThat(validator.orElseReturn(OTHER)).isEqualTo(OTHER);
    }

    @Test
    void OrElseReturnFunction_validInput_shouldReturnValue() {
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        Assertions.assertThat(validator.orElseReturn(x -> OTHER)).isEqualTo(VALUE);
    }

    @Test
    void OrElseReturnFunction_invalidInput_shouldReturnFunction() {
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        Assertions.assertThat(validator.orElseReturn(x -> OTHER)).isEqualTo(OTHER);
    }

    @Test
    void orElseLogInfo_validInput_shouldNotLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        validator.orElseLogInfo(logger);
        Mockito.verifyNoInteractions(logger);
    }

    @Test
    void orElseLogInfo_invalidInput_shouldLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        validator.orElseLogInfo(logger);
        Mockito.verify(logger).info(FAIL_MESSAGE);
    }

    @Test
    void orElseLogWarn_validInput_shouldNotLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        validator.orElseLogWarn(logger);
        Mockito.verifyNoInteractions(logger);
    }

    @Test
    void orElseLogWarn_invalidInput_shouldLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        validator.orElseLogWarn(logger);
        Mockito.verify(logger).warn(FAIL_MESSAGE);
    }

    @Test
    void orElseLogError_validInput_shouldNotLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        validator.orElseLogError(logger);
        Mockito.verifyNoInteractions(logger);
    }

    @Test
    void orElseLogError_invalidInput_shouldLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidationBuilder.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
        var validator = new EndingRuleValidationBuilder<String, String>(() -> VALUE, continuingValidator);
        validator.orElseLogError(logger);
        Mockito.verify(logger).error(FAIL_MESSAGE);
    }
}