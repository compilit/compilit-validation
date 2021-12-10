package org.solidcoding.validation.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  public void orElseLogMessage() {
    var logger = LoggerFactory.getLogger(Verifications.class);
    orElseLogMessage(logger);
  }

  @Override
  public void orElseLogMessage(final Logger logger) {
    final var isValid = validator.validate();
    if (!isValid) {
      logger.error(validator.getMessage());
      return;
    }
    if (runnable != null)
      runnable.run();
  }

}
