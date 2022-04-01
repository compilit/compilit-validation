package com.compilit.validation.api;

import com.compilit.validation.api.contracts.VoidValidationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

final class VoidRuleValidatorBuilder<T> extends AbstractLoggingValidator<T> implements VoidValidationBuilder {

  private final Runnable runnable;

  VoidRuleValidatorBuilder(final Subject<T> subject, final Runnable runnable) {
    super(subject);
    this.runnable = runnable;
  }

  @Override
  public <E extends RuntimeException> Void orElseThrow(final Function<String, E> throwableFunction) {
    final var isValid = subject.validate();
    if (!isValid) {
      throw throwableFunction.apply(subject.getMessage());
    }
    runnable.run();
    return null;
  }

  @Override
  public boolean orElseLogMessage() {
    var logger = LoggerFactory.getLogger(Verifications.class);
    return orElseLogMessage(logger);
  }

  @Override
  public boolean orElseLogMessage(final Logger logger) {
    final var isValid = subject.validate();
    if (!isValid) {
      logger.error(subject.getMessage());
    }
    if (isValid) {
      subject.processIntermediateActions();
      runnable.run();
    }
    return isValid;
  }
}
