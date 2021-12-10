package org.solidcoding.validation.api;

import org.solidcoding.validation.api.contracts.ArgumentAppender;
import org.solidcoding.validation.api.contracts.ContinuingValidationBuilder;
import org.solidcoding.validation.api.contracts.Rule;
import org.solidcoding.validation.api.contracts.ValidationBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class RuleValidationBuilder<T> implements ValidationBuilder<T> {

  private final T value;
  private final List<Rule<T>> ruleDefinitions;
  private final List<Rule.Extended<T>> xRuleDefinitions;

  public RuleValidationBuilder(final T value) {
    this.value = value;
    this.ruleDefinitions = new ArrayList<>();
    this.xRuleDefinitions = new ArrayList<>();
  }

  @Override
  public ContinuingValidationBuilder<T> compliesWith(final Rule<T> rule) {
    ruleDefinitions.add(rule);
    return new ContinuingRuleValidationBuilder<>(ruleDefinitions, value);
  }

  @Override
  public ContinuingValidationBuilder<T> compliesWith(final Collection<Rule<T>> rules) {
    ruleDefinitions.addAll(rules);
    return new ContinuingRuleValidationBuilder<>(ruleDefinitions, value);
  }

  @Override
  public ArgumentAppender<T> compliesWith(final Rule.Extended<T> rule) {
    xRuleDefinitions.add(rule);
    return new ExtendedRuleArgumentAppender<>(xRuleDefinitions, value);
  }


}
