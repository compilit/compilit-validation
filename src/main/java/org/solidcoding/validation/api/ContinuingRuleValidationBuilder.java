package org.solidcoding.validation.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

final class ContinuingRuleValidationBuilder<T> implements ContinuingValidationBuilder<T> {

  static final String DEFAULT_MESSAGE = "Nothing to report";
  private final List<Rule<T>> ruleDefinitions = new ArrayList<>();
  private final List<Rule.Extended<T>> xRuleDefinitions = new ArrayList<>();
  private final T value;
  private final Object argument;
  private final boolean isExtended;
  private String message;

  ContinuingRuleValidationBuilder(final List<Rule<T>> ruleDefinitions, final T value) {
    this.ruleDefinitions.addAll(ruleDefinitions);
    this.value = value;
    this.argument = null;
    this.isExtended = false;
  }

  ContinuingRuleValidationBuilder(final List<Rule.Extended<T>> xRuleDefinitions,
                                  final T value,
                                  final Object argument) {
    this.xRuleDefinitions.addAll(xRuleDefinitions);
    this.value = value;
    this.argument = argument;
    this.isExtended = true;
  }

  @Override
  public String getMessage() {
    if (message == null) {
      return DEFAULT_MESSAGE;
    }
    return message;
  }

  @Override
  public ContinuingValidationBuilder<T> and(final Rule<T> rule) {
    ruleDefinitions.add(rule);
    return this;
  }

  @Override
  public boolean validate() {
    message = DEFAULT_MESSAGE;
    final var stringBuilder = new StringBuilder();
    var isValid = true;
    if (isExtended) {
      for (final var biRuleDefinition : xRuleDefinitions) {
        if (!biRuleDefinition.test(value, argument)) {
          stringBuilder.append("Broken rule: ")
                       .append(biRuleDefinition.getFailMessage())
                       .append("\n");
          isValid = false;
        }
      }
    }
    for (final var ruleDefinition : ruleDefinitions) {
      if (!ruleDefinition.test(value)) {
        stringBuilder.append("Broken rule: ").append(ruleDefinition.getFailMessage()).append("\n");
        isValid = false;
      }
    }
    if (!isValid) {
      message = stringBuilder.toString();
    }
    return isValid;
  }

  @Override
  public <R> ReturningValidationBuilder<R> andThen(final Supplier<R> supplier) {
    return new EndingRuleValidationBuilder<>(supplier, this);
  }

  @Override
  public VoidEndingValidationBuilder andThen(final Runnable runnable) {
    return new VoidEndingRuleValidatorBuilder<>(runnable, this);
  }


  @Override
  public <E extends RuntimeException> Boolean orElseThrow(final Function<String, E> throwableFunction) {
    final var isValid = validate();
    if (!isValid) {
      throw throwableFunction.apply(getMessage());
    }
    return true;
  }

  @Override
  public T orElseReturn(final T other) {
    final var isValid = validate();
    if (!isValid) {
      return other;
    }
    return value;
  }

  @Override
  public T orElseReturn(final Function<String, T> other) {
    final var isValid = validate();
    if (!isValid) {
      return other.apply(message);
    }
    return value;
  }

}
