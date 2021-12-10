package org.solidcoding.validation.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solidcoding.validation.api.contracts.Rule;
import org.solidcoding.validation.api.contracts.VoidValidationBuilder;

import java.util.List;
import java.util.function.Function;

final class VoidRuleValidatorBuilder<T> extends RuleDefinitionValidator<T> implements VoidValidationBuilder {

  private final Runnable runnable;

  VoidRuleValidatorBuilder(final List<Rule<T>> ruleDefinitions, final T value, final Runnable runnable) {
    super(ruleDefinitions, value);
    this.runnable = runnable;
  }

  VoidRuleValidatorBuilder(final List<Rule.Extended<T>> xRuleDefinitions,
                           final T value,
                           final Object argument,
                           final Runnable runnable) {
    super(xRuleDefinitions, value, argument);
    this.runnable = runnable;
  }

  @Override
  public <E extends RuntimeException> Void orElseThrow(final Function<String, E> throwableFunction) {
    final var isValid = validate();
    if (!isValid) {
      throw throwableFunction.apply(getMessage());
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
    final var isValid = validate();
    if (!isValid) {
      logger.error(getMessage());
    }
    else runnable.run();
    return isValid;
  }
  
}
