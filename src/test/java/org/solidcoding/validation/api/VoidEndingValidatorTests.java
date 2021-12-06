package org.solidcoding.validation.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.fail;

class VoidEndingValidatorTests {

    private static final String FAIL_MESSAGE = "failure";

    @Test
    void orElseThrow_valid_shouldReturnVoid() {
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new VoidEndingValidator<String>(() -> System.out.println("yay"), continuingValidator);
        Assertions.assertThat(validator.orElseThrow(RuntimeException::new)).isNull();
    }

    @Test
    void orElseThrow_invalid_shouldReturnVoid() {
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        var validator = new VoidEndingValidator<String>(() -> fail(), continuingValidator);
        Assertions.assertThatThrownBy(() -> validator.orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void orElseLogInfo_validInput_shouldNotLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new VoidEndingValidator<String>(() -> System.out.println("yay"), continuingValidator);
        validator.orElseLogInfo(logger);
        Mockito.verifyNoInteractions(logger);
    }

    @Test
    void orElseLogInfo_invalidInput_shouldLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
        var validator = new VoidEndingValidator<String>(() -> fail(), continuingValidator);
        validator.orElseLogInfo(logger);
        Mockito.verify(logger).info(FAIL_MESSAGE);
    }

    @Test
    void orElseLogWarn_validInput_shouldNotLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new VoidEndingValidator<String>(() -> System.out.println("yay"), continuingValidator);
        validator.orElseLogWarn(logger);
        Mockito.verifyNoInteractions(logger);
    }

    @Test
    void orElseLogWarn_invalidInput_shouldLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
        var validator = new VoidEndingValidator<String>(() -> fail(), continuingValidator);
        validator.orElseLogWarn(logger);
        Mockito.verify(logger).warn(FAIL_MESSAGE);
    }

    @Test
    void orElseLogError_validInput_shouldNotLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(true);
        var validator = new VoidEndingValidator<String>(() -> System.out.println("yay"), continuingValidator);
        validator.orElseLogError(logger);
        Mockito.verifyNoInteractions(logger);
    }

    @Test
    void orElseLogError_invalidInput_shouldLog() {
        var logger = Mockito.mock(Logger.class);
        var continuingValidator = Mockito.mock(ContinuingValidator.class);
        Mockito.when(continuingValidator.validate()).thenReturn(false);
        Mockito.when(continuingValidator.getMessage()).thenReturn(FAIL_MESSAGE);
        var validator = new VoidEndingValidator<String>(() -> fail(), continuingValidator);
        validator.orElseLogError(logger);
        Mockito.verify(logger).error(FAIL_MESSAGE);
    }
}