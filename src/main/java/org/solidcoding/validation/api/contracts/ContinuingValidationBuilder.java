package org.solidcoding.validation.api.contracts;

import java.util.function.Supplier;

public interface ContinuingValidationBuilder<T> extends ReturningValidationBuilder<T>, Validator {

  /**
   * Connect your current predicate to another one.
   *
   * @param rule the next rule you wish to connect to the pipe.
   * @return ContinuingValidationBuilder to continue adding rules.
   */
  ContinuingValidationBuilder<T> and(Rule<T> rule);

  /**
   * Connect your current predicate to another one.
   *
   * @param rule the next rule you wish to connect to the pipe.
   * @return ContinuingValidationBuilder to continue adding rules.
   */
  ContinuingValidationBuilder<T> and(Rule.Extended<T> rule);

  /**
   * Same as validate(); but returns a custom object in the form of a supplier.
   *
   * @param supplier the supplier which encapsulated the return type.
   * @param <R>      the type you wish to return.
   * @return R in the form of a supplier.
   */
  <R> ReturningValidationBuilder<R> andThen(Supplier<R> supplier);

  /**
   * Same as validate(); but returns a custom object in the form of a supplier.
   *
   * @param runnable the runnable process which should be started after successful validation.
   * @return R in the form of a supplier.
   */
  VoidValidationBuilder andThen(Runnable runnable);

}