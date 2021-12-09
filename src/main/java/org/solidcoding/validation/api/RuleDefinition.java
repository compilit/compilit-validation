package org.solidcoding.validation.api;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

final class RuleDefinition<T> implements Rule<T> {

  private final Predicate<T> predicate;
  private final String failMessage;

  RuleDefinition(final Predicate<T> predicate, final String failMessage) {
    this.predicate = predicate;
    this.failMessage = failMessage;
  }

  @Override
  public String getFailMessage() {
    return failMessage;
  }

  @Override
  public boolean test(final T value) {
    return predicate.test(value);
  }

  static final class Extended<T> implements Rule.Extended<T> {

    private final BiPredicate<T, Object> predicate;
    private final String failMessage;

    Extended(final BiPredicate<T, Object> predicate, final String failMessage) {
      this.predicate = predicate;
      this.failMessage = failMessage;
    }

    @Override
    public String getFailMessage() {
      return failMessage;
    }

    @Override
    public boolean test(final T value, final Object argument) {
      return predicate.test(value, argument);
    }
  }
}
