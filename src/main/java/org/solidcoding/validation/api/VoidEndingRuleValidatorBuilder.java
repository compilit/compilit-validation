package org.solidcoding.validation.api;

import java.util.function.Function;

final class VoidEndingRuleValidatorBuilder<T> extends AbstractLoggingValidator<T>
        implements VoidEndingValidationBuilder {

  VoidEndingRuleValidatorBuilder(final Runnable runnable, final ContinuingValidationBuilder<T> validator) {
    super(runnable, validator);
  }

  @Override
  public <E extends RuntimeException> Void orElseThrow(final Function<String, E> throwableFunction) {
    final var isValid = validator.validate();
    if (!isValid) {
      throw throwableFunction.apply(validator.getMessage());
    }
    runnable.run();
    return null;
  }

}
