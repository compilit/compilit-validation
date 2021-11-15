package org.solidcoding.validation;

import java.util.function.Supplier;

public class EndingValidator<T, R> implements ReturningValidator<R> {

  private final Supplier<R> supplier;
  private final Validator<T> businessRuleValidator;

  public EndingValidator(Supplier<R> supplier, Validator<T> businessRuleValidator) {
    this.supplier = supplier;
    this.businessRuleValidator = businessRuleValidator;
  }

  /**
   * {@inheritDoc}
   */
  public <E extends RuntimeException> R orElseThrow(E throwable) {
    var isValid = businessRuleValidator.validate();
    if (!isValid) {
      throw throwable;
    }
    return supplier.get();
  }
}
