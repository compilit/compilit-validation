package com.compilit.validation.api;

import com.compilit.validation.api.contracts.ArgumentAppender;
import com.compilit.validation.api.contracts.ContinuingValidationBuilder;
import com.compilit.validation.api.contracts.Rule;

import java.util.List;

final class ExtendedRuleArgumentAppender<T> implements ArgumentAppender<T> {

  private final List<Rule.Extended<T>> ruleDefinitions;
  private final T value;

  ExtendedRuleArgumentAppender(final List<Rule.Extended<T>> ruleDefinitions, final T value) {
    this.ruleDefinitions = ruleDefinitions;
    this.value = value;
  }

  @Override
  public ContinuingValidationBuilder<T> whileApplying(final Object argument) {
    return new ContinuingRuleValidationBuilder<>(ruleDefinitions, value, argument);
  }
}
