package org.solidcoding.validation.api;

import org.solidcoding.validation.api.contracts.Rule;

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

  static final class Extended<T> implements Rule.Extended<T> {

    private final BiPredicate<T, Object> predicate;
    private final String message;

    Extended(final BiPredicate<T, Object> predicate, final String message) {
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
