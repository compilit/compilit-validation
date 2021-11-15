package org.solidcoding.validation;

public interface ReturningValidator<R> {

  /**
   * @param throwable the Exception that needs to be thrown when a rule is broken.
   * @param <E>       the bound of the Exception that needs to be thrown when a rule is broken.
   * @return R the return type
   */
  <E extends RuntimeException> R orElseThrow(E throwable);
}
