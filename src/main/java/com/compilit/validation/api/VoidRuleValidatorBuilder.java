package com.compilit.validation.api;

import com.compilit.validation.api.contracts.Rule;
import com.compilit.validation.api.contracts.VoidValidationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

final class VoidRuleValidatorBuilder<T> extends RuleDefinitionValidator<T> implements VoidValidationBuilder {

  private final Runnable runnable;

  VoidRuleValidatorBuilder(final List<Rule<T>> ruleDefinitions, final T value, final Consumer<T> consumer) {
    super(ruleDefinitions, value);
    this.runnable = () -> consumer.accept(value);
  }

  VoidRuleValidatorBuilder(final List<Rule.Extended<T>> xRuleDefinitions,
                           final T value,
                           final Object argument,
                           final Consumer<T> consumer) {
    super(xRuleDefinitions, value, argument);
    this.runnable = () -> consumer.accept(value);
  }

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
    if (isValid)
      runnable.run();
    return isValid;
  }

}
