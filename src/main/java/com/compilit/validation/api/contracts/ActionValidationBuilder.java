package com.compilit.validation.api.contracts;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ActionValidationBuilder<T> {

  /**
   * Same as validate(); but returns a custom object in the form of a supplier.
   *
   * @param runnable the runnable process which should be started after successful validation.
   * @return R in the form of a supplier.
   */
  VoidValidationBuilder andThen(Runnable runnable);

  /**
   * Same as validate(); but returns a custom object in the form of a supplier.
   *
   * @param supplier the supplier which encapsulated the return type.
   * @param <R>      the type you wish to return.
   * @return R in the form of a supplier.
   */
  <R> ReturningValidationBuilder<R> andThen(Supplier<R> supplier);

  /**
   * Same as validate(); but after successful validation the consumer operation is performed upon the tested value.
   *
   * @param consumer the function that should be performed after successful validation.
   */
  ReturningValidationBuilder<T> andThen(Consumer<T> consumer);

  /**
   * Same as validate(); but returns a custom object in the form of a function to operate on the tested value and return another.
   *
   * @param function the function that should be performed after successful validation.
   * @param <R>      the return value of the function.
   * @return R the return value.
   */
  <R> ReturningValidationBuilder<R> andThen(Function<T, R> function);

}
