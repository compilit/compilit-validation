package com.compilit.validation.api.contracts;

import java.util.function.Consumer;

public interface ContinuingValidationBuilder<T> extends ActionValidationBuilder<T>, ReturningValidationBuilder<T>, LoggingValidator, Validator {

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
  ContinuingValidationBuilder<T> and(Rule.WithDualInput<T> rule);

  /**
   * Performs an intermediate operation upon the tested value after successful validation.
   *
   * @param consumer the supplier which encapsulated the return type.
   * @return T in the form of a consumer.
   */
  ActionValidationBuilder<T> thenApply(Consumer<T> consumer);

}
