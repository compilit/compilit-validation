package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

class BusinessRuleValidator<T> implements Validator<T> {

  private final T value;
  private final List<Predicate<T>> rules;

  BusinessRuleValidator(T value) {
    this.value = value;
    this.rules = new ArrayList<>();
  }

  @Override
  public Validator<T> compliesWith(Predicate<T> rule) {
    rules.add(rule);
    return this;
  }

  @Override
  public Validator<T> compliesWith(List<Predicate<T>> rules) {
    this.rules.addAll(rules);
    return this;
  }

  @Override
  public boolean validate() {
    return rules.stream().allMatch(x -> x.test(value));
  }

  @Override
  public <R> ReturningValidator<R> andThen(Supplier<R> supplier) {
      return new EndingValidator<>(supplier, this);
  }

  @Override
  public <E extends RuntimeException> boolean orElseThrow(E throwable) {
    var isValid = validate();
    if (!isValid) {
      throw throwable;
    }
    return true;
  }

}
