package org.solidcoding.validation.api;

public interface ArgumentValidationBuilder<T> {

  ContinuingValidationBuilder<T> whileApplying(Object argument);
}
