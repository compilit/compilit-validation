package org.solidcoding.validation.api;

import org.slf4j.Logger;

abstract class AbstractLoggingValidator<T> implements LoggingValidator {

  protected final ContinuingValidationBuilder<T> validator;

  protected AbstractLoggingValidator(final ContinuingValidationBuilder<T> validator) {
    this.validator = validator;
  }

  @Override
  public void orElseLogInfo(final Logger logger) {
    final var isValid = validator.validate();
    if (!isValid) {
      logger.info(validator.getMessage());
    }
  }

  @Override
  public void orElseLogWarn(final Logger logger) {
    final var isValid = validator.validate();
    if (!isValid) {
      logger.warn(validator.getMessage());
    }
  }

  @Override
  public void orElseLogError(final Logger logger) {
    final var isValid = validator.validate();
    if (!isValid) {
      logger.error(validator.getMessage());
    }
  }

}
