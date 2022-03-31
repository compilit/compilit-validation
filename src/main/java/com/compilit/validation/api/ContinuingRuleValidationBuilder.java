package com.compilit.validation.api;

import com.compilit.validation.api.contracts.ContinuingValidationBuilder;
import com.compilit.validation.api.contracts.ReturningValidationBuilder;
import com.compilit.validation.api.contracts.Rule;
import com.compilit.validation.api.contracts.VoidValidationBuilder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

final class ContinuingRuleValidationBuilder<T> extends RuleDefinitionValidator<T> implements ContinuingValidationBuilder<T> {

  ContinuingRuleValidationBuilder(final List<Rule<T>> ruleDefinitions, final T value) {
    super(ruleDefinitions, value);
  }

  ContinuingRuleValidationBuilder(final List<Rule.Extended<T>> xRuleDefinitions,
                                  final T value,
                                  final Object argument) {
    super(xRuleDefinitions, value, argument);
  }

  @Override
  public ContinuingValidationBuilder<T> and(final Rule<T> rule) {
    ruleDefinitions.add(rule);
    return this;
  }

  @Override
  public ContinuingValidationBuilder<T> and(Rule.Extended<T> rule) {
    xRuleDefinitions.add(rule);
    return this;
  }

  @Override
  public VoidValidationBuilder andThen(final Consumer<T> consumer) {
    if (isExtended)
      return new VoidRuleValidatorBuilder<>(xRuleDefinitions, value, argument, consumer);
    return new VoidRuleValidatorBuilder<>(ruleDefinitions, value, consumer);
  }

  @Override
  public <R> ReturningValidationBuilder<R> andThen(final Supplier<R> supplier) {
    if (isExtended)
      return new ReturningRuleValidationBuilder<>(xRuleDefinitions, value, argument, supplier);
    return new ReturningRuleValidationBuilder<>(ruleDefinitions, value, supplier);
  }

  @Override
  public VoidValidationBuilder andThen(final Runnable runnable) {
    if (isExtended)
      return new VoidRuleValidatorBuilder<>(xRuleDefinitions, value, argument, runnable);
    return new VoidRuleValidatorBuilder<>(ruleDefinitions, value, runnable);
  }

  @Override
  public <R> ReturningValidationBuilder<R> andThen(final Function<T, R> function) {
    if (isExtended)
      return new ReturningRuleValidationBuilder<>(xRuleDefinitions, value, argument, function);
    return new ReturningRuleValidationBuilder<>(ruleDefinitions, value, function);
  }

  @Override
  public <E extends RuntimeException> T orElseThrow(final Function<String, E> throwableFunction) {
    final var isValid = validate();
    if (!isValid) {
      throw throwableFunction.apply(getMessage());
    }
    return value;
  }

  @Override
  public T orElseReturn(final T other) {
    final var isValid = validate();
    if (!isValid) {
      return other;
    }
    return value;
  }

  @Override
  public T orElseReturn(final Function<String, T> other) {
    final var isValid = validate();
    if (!isValid) {
      return other.apply(message);
    }
    return value;
  }

}
