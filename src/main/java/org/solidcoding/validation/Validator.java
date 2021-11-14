package org.solidcoding.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class Validator<T> {

  private final T value;
  private final List<Predicate<T>> rules;

  private Validator(T value) {
    this.value = value;
    this.rules = new ArrayList<>();
  }

  /**
   * @param value the value on which to apply the rules.
   * @param <T>   the type of the value.
   * @return a Validator to add rules to.
   */
  public static <T> Validator<T> makeSure(T value) {
    return new Validator<>(value);
  }

  /**
   * @param rule the rule which the value needs to comply with.
   * @return the Validator to add more rules.
   */
  public Validator<T> compliesWith(Predicate<T> rule) {
    rules.add(rule);
    return this;
  }

  /**
   * @return true if all rules pass.
   */
  public boolean validate() {
    return rules.stream().allMatch(x -> x.test(value));
  }

  /**
   * @param throwable the Exception that needs to be thrown when a rule is broken.
   * @param <E>       the bound of the Exception that needs to be thrown when a rule is broken.
   * @return true if all rules pass.
   */
  public <E extends RuntimeException> boolean orElseThrow(E throwable) {
    var isValid = validate();
    if (!isValid) {
      throw throwable;
    }
    return true;
  }
}
