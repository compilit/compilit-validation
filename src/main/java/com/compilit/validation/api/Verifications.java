package com.compilit.validation.api;

import com.compilit.validation.api.contracts.ValidationBuilder;

public final class Verifications {

  private Verifications() {
  }

  /**
   * @param value the value on which to apply the rules.
   * @param <T>   the type of the value.
   * @return a Validator to add rules to.
   */
  public static <T> ValidationBuilder<T> verifyThat(final T value) {
    return new RuleValidationBuilder<>(value);
  }

}
