package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class Validator<T> {

  private final T value;
  private final List<Rule<T>> rules;

  private Validator(T value) {
    this.value = value;
    this.rules = new ArrayList<>();
  }

  public static <T> Validator<T> makeSure(T value) {
    return new Validator<>(value);
  }

  public Validator<T> compliesWith(Rule<T> rule) {
    rules.add(rule);
    return this;
  }

  public boolean validate() {
    return rules.stream().allMatch(x -> x.validate(value));
  }

  public <E extends RuntimeException> boolean orElseThrow(Supplier<E> runnable) {
    var isValid = validate();
    if (!isValid) {
      throw runnable.get();
    }
    return true;
  }
}
