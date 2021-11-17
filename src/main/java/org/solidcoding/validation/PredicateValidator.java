package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

class PredicateValidator<T> implements Validator<T> {

 
  private String message = "Nothing to report";
  private final T value;
  private final List<Predicate<T>> rules;
  private final List<Rule<T>> ruleDefinitions;

  PredicateValidator(T value) {
    this.value = value;
    this.rules = new ArrayList<>();
    this.ruleDefinitions = new ArrayList<>();
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public Validator<T> compliesWith(Predicate<T> rule) {
    rules.add(rule);
    return this;
  }

  @Override
  public Validator<T> compliesWith(Predicate<T> rule, String failMessage) {
    ruleDefinitions.add(new RuleDefinition<>(rule, failMessage));
    return this;
  }

  @Override
  public Validator<T> compliesWith(List<Predicate<T>> rules) {
    this.rules.addAll(rules);
    return this;
  }

  @Override
  public boolean validate() {
    for (var ruleDefinition : ruleDefinitions) {
      if (!ruleDefinition.getPredicate().test(value)) {
        message = ruleDefinition.getMessage();
        return false;
      }
    }
    return true;
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

  @Override
  public T orElseReturn(T other) {
    var isValid = validate();
    if (!isValid) {
      return other;
    }
    return value;
  }
}
