package com.compilit.validation.api;

import com.compilit.validation.api.contracts.ReturningValidationBuilder;
import com.compilit.validation.api.contracts.Rule;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

final class ReturningRuleValidationBuilder<T, R> extends RuleDefinitionValidator<T> implements ReturningValidationBuilder<R> {

  private final Supplier<R> supplier;

  ReturningRuleValidationBuilder(final List<Rule<T>> ruleDefinitions, final T value, final Supplier<R> supplier) {
    super(ruleDefinitions, value);
    this.supplier = supplier;
  }

  ReturningRuleValidationBuilder(final List<Rule.Extended<T>> xRuleDefinitions,
                                 final T value,
                                 final Object argument,
                                 final Supplier<R> supplier) {
    super(xRuleDefinitions, value, argument);
    this.supplier = supplier;
  }


  @Override
  public <E extends RuntimeException> R orElseThrow(final Function<String, E> throwableFunction) {
    final var isValid = validate();
    if (!isValid) {
      throw throwableFunction.apply(getMessage());
    }
    return supplier.get();
  }

  @Override
  public R orElseReturn(final R other) {
    final var isValid = validate();
    if (!isValid) {
      return other;
    }
    return supplier.get();
  }

  @Override
  public R orElseReturn(final Function<String, R> other) {
    final var isValid = validate();
    final var message = getMessage();
    if (!isValid) {
      return other.apply(message);
    }
    return supplier.get();
  }

}
