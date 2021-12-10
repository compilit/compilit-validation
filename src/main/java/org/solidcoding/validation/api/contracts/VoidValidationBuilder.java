package org.solidcoding.validation.api.contracts;

import java.util.function.Function;

public interface VoidValidationBuilder extends ThrowingValidator<Void>, LoggingValidator {

  /**
   * @param throwableFunction the function defining the Exception that needs to be thrown when a
   *                          rule is broken. The String is the stored failure message of the
   *                          validation.
   * @param <E>               the bound of the Exception that needs to be thrown when a rule is
   *                          broken.
   */
  @Override
  <E extends RuntimeException> Void orElseThrow(Function<String, E> throwableFunction);

}
