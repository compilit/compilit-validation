package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class PredicateValidator<T> implements Validator<T> {

  private String message = "Nothing to report";
  private final T value;
  private final List<Rule<T>> ruleDefinitions;

  PredicateValidator(T value) {
    this.value = value;
    this.ruleDefinitions = new ArrayList<>();
  }

  @Override
  public Validator<T> compliesWith(Predicate<T> rule) {
    ruleDefinitions.add(new RuleDefinition<>(rule, message));
    return this;
  }

  @Override
  public Validator<T> compliesWith(Predicate<T> rule, String failMessage) {
    ruleDefinitions.add(new RuleDefinition<>(rule, failMessage));
    return this;
  }

  @Override
  public Validator<T> compliesWith(List<Predicate<T>> rules) {
    rules.forEach(x -> ruleDefinitions.add(new RuleDefinition<>(x, message)));
    return this;
  }

  @Override
  public boolean validate() {
    for (var ruleDefinition : ruleDefinitions) {
      if (!ruleDefinition.test(value)) {
        message = ruleDefinition.getFailMessage();
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

  @Override
  public T orElseReturn(Function<String, T> other) {
    var isValid = validate();
    var message = this.message;
    if (!isValid) {
      return other.apply(message);
    }
    return value;
  }

}
