package org.solidcoding.validation;

import java.util.function.Predicate;

class BusinessRule<T> implements Rule<T> {

  private final Predicate<T> rule;

  BusinessRule(Predicate<T> rule) {
    this.rule = rule;
  }

  @Override
  public boolean validate(T value) {
    return rule.test(value);
  }

}
