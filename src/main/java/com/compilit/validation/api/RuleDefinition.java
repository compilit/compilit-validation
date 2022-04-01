package com.compilit.validation.api;

import com.compilit.validation.api.contracts.Rule;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

final class RuleDefinition<T> implements Rule<T> {

  private final Predicate<T> predicate;
  private final String message;

  RuleDefinition(final Predicate<T> predicate, final String message) {
    this.predicate = predicate;
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public boolean test(final T value) {
    return predicate.test(value);
  }

  static final class WithDualInput<T> implements Rule.WithDualInput<T> {

    private final BiPredicate<T, Object> predicate;
    private final String message;

    WithDualInput(final BiPredicate<T, Object> predicate, final String message) {
      this.predicate = predicate;
      this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public boolean test(final T value, final Object argument) {
      return predicate.test(value, argument);
    }
  }

}
