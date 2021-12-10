package org.solidcoding.validation.api;

import org.slf4j.Logger;

abstract class AbstractLoggingValidator<T> implements LoggingValidator {

  protected final ContinuingValidationBuilder<T> validator;
  protected final Runnable runnable;

  protected AbstractLoggingValidator(final ContinuingValidationBuilder<T> validator) {
    this.validator = validator;
    this.runnable = null;
  }

  protected AbstractLoggingValidator(final Runnable runnable, final ContinuingValidationBuilder<T> validator) {
    this.validator = validator;
    this.runnable = runnable;
  }

  @Override
  public void orElseLogInfo(final Logger logger) {
    final var isValid = validator.validate();
    if (!isValid) {
      logger.info(validator.getMessage());
      return;
    }
    if (runnable != null)
      runnable.run();
  }

  @Override
  public void orElseLogWarn(final Logger logger) {
    final var isValid = validator.validate();
    if (!isValid) {
      logger.warn(validator.getMessage());
      return;
    }
    if (runnable != null)
      runnable.run();
  }

  @Override
  public void orElseLogError(final Logger logger) {
    final var isValid = validator.validate();
    if (!isValid) {
      logger.error(validator.getMessage());
      return;
    }
    if (runnable != null)
      runnable.run();
  }

}
