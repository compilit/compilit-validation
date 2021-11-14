package org.solidcoding.validation;

import java.util.function.Predicate;

class BusinessRule<T> implements Predicate<T> {

  private final Predicate<T> rule;

  BusinessRule(Predicate<T> rule) {
    this.rule = rule;
  }

  @Override
  public boolean test(T value) {
    return rule.test(value);
  }

}
