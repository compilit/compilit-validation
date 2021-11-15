package org.solidcoding.validation;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Validator<T> {

  /**
   * @param value the value on which to apply the rules.
   * @param <T>   the type of the value.
   * @return a Validator to add rules to.
   */
  static <T> Validator<T> makeSure(T value) {
    return new BusinessRuleValidator<>(value);
  }

  /**
   * @param rule the rule which the value needs to comply with.
   * @return the Validator to add more rules.
   */
  Validator<T> compliesWith(Predicate<T> rule);

  /**
   * @param rules the rules which the value needs to comply with.
   * @return the Validator to add more rules.
   */
  Validator<T> compliesWith(List<Predicate<T>> rules);

  /**
   * @return boolean true if all rules pass. False if at least one rule fails.
   */
  boolean validate();

  /**
   * Same as validate(); but returns a custom object in the form of a supplier;
   * @return R in the form of a supplier.
   */
  <R> ReturningValidator<R> andThen(Supplier<R> supplier);

  /**
   * @param throwable the Exception that needs to be thrown when a rule is broken.
   * @param <E>       the bound of the Exception that needs to be thrown when a rule is broken.
   * @return true if all rules pass.
   */
  <E extends RuntimeException> boolean orElseThrow(E throwable);
}
