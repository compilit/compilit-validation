package org.solidcoding.validation;

import java.util.function.Predicate;

class RuleDefinition<T> implements Rule<T> {

  private final Predicate<T> predicate;
  private final String failMessage;

  RuleDefinition(Predicate<T> predicate, String failMessage) {
    this.predicate = predicate;
    this.failMessage = failMessage;
  }

  @Override
  public String getFailMessage() {
    return failMessage;
  }

  @Override
  public boolean test(T value) {
    return predicate.test(value);
  }

}
